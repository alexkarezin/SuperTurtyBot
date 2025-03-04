package dev.darealturtywurty.superturtybot.commands.minigames;

import dev.darealturtywurty.superturtybot.core.api.ApiHandler;
import dev.darealturtywurty.superturtybot.core.api.pojo.Region;
import dev.darealturtywurty.superturtybot.core.api.request.RegionExcludeRequestData;
import dev.darealturtywurty.superturtybot.core.command.CommandCategory;
import dev.darealturtywurty.superturtybot.core.command.CoreCommand;
import dev.darealturtywurty.superturtybot.core.util.Constants;
import dev.darealturtywurty.superturtybot.core.util.Either;
import io.javalin.http.HttpStatus;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GuessCombinedFlagsCommand extends CoreCommand {
    private static final Map<Long, Game> GAMES = new HashMap<>();

    public GuessCombinedFlagsCommand() {
        super(new Types(true, false, false, false));
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MINIGAMES;
    }

    @Override
    public String getDescription() {
        return "Guess the regions that make up the combined flag!";
    }

    @Override
    public String getName() {
        return "guesscombinedflags";
    }

    @Override
    public String getRichName() {
        return "Guess The Combined Flags";
    }

    @Override
    public List<OptionData> createOptions() {
        return List.of(
                new OptionData(OptionType.INTEGER, "number", "The number of flags to combine", false).setRequiredRange(2, 16),
                new OptionData(OptionType.BOOLEAN, "exclude-territories", "Whether to exclude territories", false),
                new OptionData(OptionType.BOOLEAN, "exclude-countries", "Whether to exclude countries", false)
        );
    }

    @Override
    public Pair<TimeUnit, Long> getRatelimit() {
        return Pair.of(TimeUnit.SECONDS, 5L);
    }

    @Override
    protected void runSlash(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild() || event.getGuild() == null) {
            reply(event, "❌ This command can only be used in a guild!", false, true);
            return;
        }

        if (event.getChannel() instanceof ThreadChannel) {
            reply(event, "❌ This command cannot be used in a thread!", false, true);
            return;
        }

        // check that the user does not already have a game running
        if (GAMES.values().stream().anyMatch(game -> game.getUserId() == event.getUser().getIdLong())) {
            reply(event, "❌ You already have a game running!", false, true);
            return;
        }

        event.deferReply().queue();

        boolean excludeTerritories = event.getOption("exclude-territories", false, OptionMapping::getAsBoolean);
        boolean excludeCountries = event.getOption("exclude-countries", false, OptionMapping::getAsBoolean);
        if (excludeTerritories && excludeCountries) {
            event.getHook().sendMessage("❌ You cannot exclude both territories and countries!").queue();
            return;
        }

        int numberOfRegions = event.getOption("number", ThreadLocalRandom.current().nextInt(2, 17), OptionMapping::getAsInt);
        final Map<String, BufferedImage> regions = new HashMap<>(numberOfRegions);

        RegionExcludeRequestData.Builder builder = new RegionExcludeRequestData.Builder();
        if (excludeTerritories) {
            builder.excludeTerritories();
        } else if (excludeCountries) {
            builder.excludeCountries();
        }

        RegionExcludeRequestData data = builder.build();

        int attempts = 0;
        for (int index = 0; index < numberOfRegions; index++) {
            Either<Pair<BufferedImage, Region>, HttpStatus> result = ApiHandler.getFlag(data);
            attempts++;
            if (result.isLeft()) {
                Pair<BufferedImage, Region> pair = result.getLeft();
                if (regions.containsKey(pair.getRight().getName())) {
                    index--;
                } else {
                    regions.put(pair.getRight().getName(), pair.getLeft());
                }
            } else {
                index--;
            }

            if (attempts >= numberOfRegions * 4) {
                event.getHook().sendMessage("❌ Could not find enough regions!").queue();
                return;
            }
        }

        // get the largest with and height using stream
        int width = regions.values().stream().mapToInt(BufferedImage::getWidth).max().orElse(0);
        int height = regions.values().stream().mapToInt(BufferedImage::getHeight).max().orElse(0);

        ByteArrayOutputStream boas = createImage(regions.values().stream().toList(), width, height);
        var upload = FileUpload.fromData(boas.toByteArray(), "combined_flags.png");

        String toSend = String.format("Guess the regions that make up the combined flag! (There are %d regions)",
                numberOfRegions);
        event.getHook().editOriginal(toSend).setFiles(upload).queue(message -> {
            message.createThreadChannel(event.getUser().getName() + "'s game").queue(thread -> {
                Either<List<Region>, HttpStatus> matchingRegions = ApiHandler.getAllRegions(data);
                if (matchingRegions.isRight()) {
                    Constants.LOGGER.error("An error occurred while trying to get all regions! Status code: {}",
                            matchingRegions.getRight().getCode());
                    event.getHook().sendMessage("❌ An error occurred while trying to get all regions!").queue(ignored -> thread.delete().queue());
                    return;
                }

                var game = new Game(regions, event.getGuild().getIdLong(),
                        event.getChannel().getIdLong(), thread.getIdLong(), message.getIdLong(),
                        event.getUser().getIdLong(), matchingRegions.getLeft());

                GAMES.put(message.getIdLong(), game);

                message.editMessageComponents(
                                ActionRow.of(Button.danger("combined-flags-" + message.getId(), Emoji.fromFormatted("❌"))))
                        .queue();

                thread.sendMessage("✅ Game started! " + event.getUser().getAsMention()).queue();

                try {
                    boas.close();
                    upload.close();
                } catch (IOException exception) {
                    Constants.LOGGER.error(
                            "An error occurred while trying to close the ByteArrayOutputStream or FileUpload!",
                            exception);
                }
            });
        });
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.isFromGuild() || event.getGuild() == null) return;
        if (event.getButton().getId() == null) return;
        if (!event.getButton().getId().startsWith("combined-flags-")) return;

        long messageId = Long.parseLong(event.getButton().getId().replace("combined-flags-", ""));

        Game game = GAMES.get(messageId);
        if (game == null) return;

        if (game.getUserId() != event.getUser().getIdLong()) {
            event.deferEdit().setComponents(event.getMessage().getComponents()).queue();
            return;
        }

        GAMES.remove(messageId, game);

        ThreadChannel thread = event.getGuild().getThreadChannelById(game.getChannelId());
        if (thread == null) return;

        String regionsStr = String.join(", ", game.getRegions().keySet());
        thread.sendMessage(String.format("Game cancelled! The regions were: %s", regionsStr)).setComponents()
                .queue($ -> thread.getManager().setArchived(true).setLocked(true).queue());

        event.editComponents().queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;

        // check if the user has a game running
        Game game = GAMES.values().stream()
                .filter(g -> g.getUserId() == event.getAuthor().getIdLong() && g.getGuildId() == event.getGuild()
                        .getIdLong() && g.getChannelId() == event.getChannel().getIdLong()).findFirst().orElse(null);
        if (game == null) return;

        // check if the message is a valid region
        String region = event.getMessage().getContentRaw().trim();

        // check if the region is valid
        if (game.getPossibleRegions().stream().map(Region::getName).noneMatch(r -> r.equalsIgnoreCase(region))) return;

        // check if the region has already been guessed
        if (game.getGuesses().stream().anyMatch(r -> r.equalsIgnoreCase(region))) return;

        // add the region to the game
        if (game.chooseRegion(region)) {
            event.getMessage().reply("✅ Correct guess!").queue();

            // check if the game has ended
            if (game.hasWon()) {
                // remove the game from the map
                GAMES.remove(game.getMessageId(), game);

                event.getChannel()
                        .sendMessage("✅ You win! The regions were: " + String.join(", ", game.getRegions().keySet()))
                        .queue($ -> ((ThreadChannel) event.getChannel()).getManager().setArchived(true).setLocked(true).queue());

                // remove the button
                TextChannel channel = event.getJDA().getTextChannelById(game.getOwnerChannelId());
                if (channel == null) return;

                channel.retrieveMessageById(game.getMessageId()).queue(message -> message.editMessageComponents().queue());
            } else {
                List<BufferedImage> images = new ArrayList<>();
                for (String region1 : game.getRegions().keySet()) {
                    if (game.getGuesses().stream().noneMatch(region2 -> region2.equalsIgnoreCase(region1))) {
                        images.add(game.getRegions().get(region1));
                    }
                }

                int width = images.stream().mapToInt(BufferedImage::getWidth).max().orElse(0);
                int height = images.stream().mapToInt(BufferedImage::getHeight).max().orElse(0);

                ByteArrayOutputStream baos = createImage(images, width, height);
                var upload = FileUpload.fromData(baos.toByteArray(), "combined_flags.png");

                String toSend = String.format(
                        "Guess the regions that make up the combined flag! (There are %d regions remaining)",
                        images.size());

                event.getChannel().sendMessage(toSend).setFiles(upload).queue($ -> {
                    try {
                        baos.close();
                        upload.close();
                    } catch (IOException exception) {
                        Constants.LOGGER.error(
                                "An error occurred while trying to close the ByteArrayOutputStream or FileUpload!",
                                exception);
                    }
                });
            }
        } else {
            event.getMessage().reply("❌ Was not a correct guess!").queue();

            if (game.getIncorrectGuesses() >= 9) {
                GAMES.remove(game.getMessageId());

                event.getChannel().sendMessage(
                                "❌ The game has ended! The regions were: " + String.join(", ", game.getRegions().keySet()))
                        .queue($ -> ((ThreadChannel) event.getChannel()).getManager().setArchived(true).setLocked(true)
                                .queue());

                // remove the button
                TextChannel channel = event.getJDA().getTextChannelById(game.getOwnerChannelId());
                if (channel == null) return;

                channel.retrieveMessageById(game.getMessageId()).queue(message -> message.editMessageComponents().queue());
                return;
            }

            if (game.getIncorrectGuesses() % 3 == 0) {
                event.getChannel().sendMessage(
                        "The regions that have been guessed are: " + String.join(", ", game.getGuesses())).queue();
            }
        }
    }

    private static ByteArrayOutputStream createImage(List<BufferedImage> images, int width, int height) {
        int numberOfRegions = images.size();

        // create a new image with the largest width and height
        var combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // draw the images onto the combined image with transparency
        Graphics2D graphics = combined.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw the background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, combined.getWidth(), combined.getHeight());

        Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f / numberOfRegions);
        graphics.setComposite(composite);

        for (var image : images) {
            graphics.drawImage(image, 0, 0, null);
        }

        graphics.dispose();

        var boas = new ByteArrayOutputStream();
        try {
            ImageIO.write(combined, "png", boas);
            boas.flush();
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "An error occurred while trying to write the combined image to a " + "ByteArrayOutputStream!",
                    exception);
        }

        return boas;
    }

    @Override
    public boolean isServerOnly() {
        return true;
    }

    private static class Game {
        private final Map<String, BufferedImage> regions;
        private final long guildId, ownerChannelId, channelId, messageId, userId;
        private final List<String> guesses = new ArrayList<>();
        private final List<Region> possibleRegions;
        private int incorrectGuesses = 0;

        public Game(Map<String, BufferedImage> regions, long guildId, long ownerChannelId, long channelId, long messageId, long userId, List<Region> possibleRegions) {
            this.regions = regions;
            this.guildId = guildId;
            this.ownerChannelId = ownerChannelId;
            this.channelId = channelId;
            this.messageId = messageId;
            this.userId = userId;
            this.possibleRegions = possibleRegions;
        }

        public Map<String, BufferedImage> getRegions() {
            return this.regions;
        }

        public long getGuildId() {
            return this.guildId;
        }

        public long getOwnerChannelId() {
            return this.ownerChannelId;
        }

        public long getChannelId() {
            return this.channelId;
        }

        public long getMessageId() {
            return this.messageId;
        }

        public long getUserId() {
            return this.userId;
        }

        public List<String> getGuesses() {
            return this.guesses;
        }

        public int getIncorrectGuesses() {
            return this.incorrectGuesses;
        }

        public List<Region> getPossibleRegions() {
            return this.possibleRegions;
        }

        public boolean chooseRegion(String region) {
            if (this.guesses.contains(region)) {
                return false;
            }

            this.guesses.add(region);

            for (var c : this.regions.keySet()) {
                if (c.equalsIgnoreCase(region)) {
                    return true;
                }
            }

            this.incorrectGuesses++;
            return false;
        }

        public boolean hasWon() {
            List<String> regions = this.regions.keySet().stream().map(String::toLowerCase).map(String::trim).toList();
            List<String> guesses = this.guesses.stream().map(String::toLowerCase).map(String::trim).toList();

            return new HashSet<>(guesses).containsAll(regions);
        }
    }
}
