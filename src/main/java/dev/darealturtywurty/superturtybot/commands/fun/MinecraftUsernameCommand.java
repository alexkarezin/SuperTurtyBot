package dev.darealturtywurty.superturtybot.commands.fun;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import dev.darealturtywurty.superturtybot.core.command.CommandCategory;
import dev.darealturtywurty.superturtybot.core.command.CoreCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.apache.commons.lang3.tuple.Pair;

public class MinecraftUsernameCommand extends CoreCommand {
    public MinecraftUsernameCommand() {
        super(new Types(true, false, false, false));
    }

    @Override
    public List<OptionData> createOptions() {
        return List.of(new OptionData(OptionType.STRING, "uuid", "The UUID used to get this user's name from", true));
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public String getDescription() {
        return "Gets the Minecraft Username from a UUID.";
    }

    @Override
    public String getHowToUse() {
        return "/mc-username [uuid]";
    }

    @Override
    public String getName() {
        return "mc-username";
    }

    @Override
    public String getRichName() {
        return "Minecraft Username";
    }

    @Override
    public Pair<TimeUnit, Long> getRatelimit() {
        return Pair.of(TimeUnit.SECONDS, 5L);
    }

    @Override
    protected void runSlash(SlashCommandInteractionEvent event) {
        final String uuid = URLEncoder.encode(event.getOption("uuid").getAsString().trim(), StandardCharsets.UTF_8);
        try {
            final URLConnection connection = new URL("https://minecraft-api.com/api/pseudo/" + uuid).openConnection();
            final String response = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
            if (response.contains("not found"))
                throw new IllegalArgumentException(response);

            event.deferReply()
                .addEmbeds(new EmbedBuilder().setTimestamp(Instant.now()).setColor(Color.BLUE)
                    .setDescription(
                        "The UUID for `" + event.getOption("uuid").getAsString() + "` is: `" + response + "`")
                    .build())
                .mentionRepliedUser(false).queue();
        } catch (final IOException exception) {
            event.deferReply(true)
                .setContent("There has been an issue trying to gather this information from our database! "
                    + "This has been reported to the bot owner!")
                .mentionRepliedUser(false).queue();
        } catch (final IllegalArgumentException exception) {
            event.deferReply(true).setContent("This player does not exist!").mentionRepliedUser(false).queue();
        }
    }
}
