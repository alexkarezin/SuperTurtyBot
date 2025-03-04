package dev.darealturtywurty.superturtybot.commands.image;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonObject;

import dev.darealturtywurty.superturtybot.core.util.Constants;
import dev.darealturtywurty.superturtybot.core.util.RedditUtils;
import dev.darealturtywurty.superturtybot.registry.Registry;
import net.dean.jraw.references.SubredditReference;
import net.dean.jraw.tree.RootCommentNode;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.RestAction;

public class ImageCommandRegistry {
    private static final Registry<ImageCommandType> IMAGE_CMD_TYPES = new Registry<>();
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> BLEP = (event, cmd) -> {
        final SubredditReference subreddit = RedditUtils.getSubreddit("Blep");
        final RootCommentNode post = RedditUtils.findValidPost(subreddit, "Blep");
        final String mediaURL = post.getSubject().getUrl().isBlank() ? post.getSubject().getThumbnail()
            : post.getSubject().getUrl();
        event.deferReply().setContent(mediaURL).mentionRepliedUser(false).queue();
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> BUNNY = (event, cmd) -> {
        try {
            final URLConnection connection = new URL(
                ThreadLocalRandom.current().nextBoolean() ? "https://api.bunnies.io/v2/loop/random/?media=mp4"
                    : "https://api.bunnies.io/v2/loop/random/?media=gif").openConnection();
            final JsonObject result = Constants.GSON.fromJson(new InputStreamReader(connection.getInputStream()),
                JsonObject.class);
            final String url = result.get("media").getAsJsonObject().get("poster").getAsString();
            event.deferReply().setContent(url).mentionRepliedUser(false).queue();
        } catch (final IOException exception) {
            exception.printStackTrace();
            event.deferReply(true).setContent("❌ There has been an issue gathering this bunny image.")
                .mentionRepliedUser(false).queue();
        }
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> CAT_BOMB = (event, cmd) -> {
        event.deferReply().setContent("Loading cats...").queue();
        final String[] subreddits = { "Cats", "Kitten", "CatSpotting", "IllegallySmolCats", "IllegallyBigCats",
            "IllegallyLongCats", "Kitty", "GrumpyCats", "TruckerCats", "FromKittenToCat", "WetCats", "SeniorCats",
            "SleepingCats", "Displeased_Kitties", "FluffyCats", "SuspiciousCats", "CatsCalledFood", "catshuggingcats",
            "SadCats", "PirateKitties", "meowormyson", "goodcats", "pimpcats", "C_AT", "thisismylifemeow", "fatcats",
            "nowmycat", "divorcedcats", "notmycat", "ChristmasCats", "scrungycats", "AdorableCats", "AliveNamedCats",
            "Medieval_Cats" };
        
        final var actions = new ArrayList<RestAction<Message>>();
        for (int index = 0; index < ThreadLocalRandom.current().nextInt(3, 7); index++) {
            final SubredditReference subreddit = RedditUtils.getRandomSubreddit(subreddits);
            final RootCommentNode post = RedditUtils.findValidPost(subreddit, subreddits);
            final String mediaURL = post.getSubject().getUrl().isBlank() ? post.getSubject().getThumbnail()
                : post.getSubject().getUrl();
            actions.add(event.getChannel().sendMessage(mediaURL));
        }
        
        RestAction.allOf(actions).queue(success -> event.getHook().deleteOriginal().queue());
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> CAT = (event, cmd) -> {
        try {
            final String url = "https://cataas.com";
            final URLConnection connection = new URL(url + "/cat?json=true").openConnection();
            final JsonObject result = Constants.GSON.fromJson(new InputStreamReader(connection.getInputStream()),
                JsonObject.class);
            final String imageId = result.get("url").getAsString();
            event.deferReply().setContent(url + imageId).mentionRepliedUser(false).queue();
        } catch (final IOException exception) {
            exception.printStackTrace();
            event.deferReply(true).setContent("❌ There has been an issue gathering this cat image.")
                .mentionRepliedUser(false).queue();
        }
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> DOG_BOMB = (event, cmd) -> {
        event.deferReply().setContent("Loading dogs...").queue();
        final String[] subreddits = { "Dogs", "lookatmydog", "dogpictures", "dogswearinghats", "dogswitheyebrows",
            "DogsWithUnderbites", "woofbarkwoof", "beachdogs", "dogswatchingyoueat", "airedale", "akita",
            "alaskanmalamute", "Americanbulldog", "americaneskimo/", "AmericanBully", "AmStaffPitts",
            "australiancattledog", "Australianshepherd", "basenji", "basset", "beagle", "beardedcollies", "Beauceron",
            "bergerbelge", "bernesemountaindogs", "bichonfrise", "BlackMouthCur", "BorderCollie", "BorderTerrier",
            "BostonTerrier", "boxer", "BrittanySpaniel", "buhund", "bulldogs", "GifsofBulldogs", "bullterrier",
            "Catahoula", "AllAboutCBRs", "chihuahua", "chinesecrested", "chowchow", "clumberspaniels", "cockerspaniel",
            "coonhounds", "corgi", "dachshund", "dalmatians", "dobermanpinscher", "EnglishSetter", "frogdogs", "galgos",
            "germanshepherds", "goldenretrievers", "greatdanes", "greatpyrenees", "greyhounds", "hounds", "irishsetter",
            "italiangreyhounds", "jackrussellterrier", "jindo", "kangal", "kelpie", "labrador", "labradors", "labs",
            "leos", "maltese", "Mastiff", "MiniaturePinscher", "mutt", "neapolitanmastiff", "newfoundlander",
            "papillon", "pitbulls", "pomeranians", "poodles", "presacanario", "pug", "ratterriers", "Rottweiler",
            "roughcollies", "samoyeds", "schipperke", "schnauzers", "scottishterriers", "shiba", "shihtzu",
            "siberianhusky", "sighthounds", "springerspaniel", "StandardPoodles", "SwissMountainDogs", "tollers",
            "toyfoxterriers", "vizsla", "weimaraner", "welshterrier", "WestHighlandTerriers", "whippets",
            "xoloitzquintli" };
        
        final var actions = new ArrayList<RestAction<Message>>();
        for (int index = 0; index < ThreadLocalRandom.current().nextInt(3, 7); index++) {
            final SubredditReference subreddit = RedditUtils.getRandomSubreddit(subreddits);
            final RootCommentNode post = RedditUtils.findValidPost(subreddit, subreddits);
            final String mediaURL = post.getSubject().getUrl().isBlank() ? post.getSubject().getThumbnail()
                : post.getSubject().getUrl();
            actions.add(event.getChannel().sendMessage(mediaURL));
        }
        
        RestAction.allOf(actions).queue(success -> event.getHook().deleteOriginal().queue());
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> DOG = (event, cmd) -> {
        try {
            final URLConnection connection = new URL("https://dog.ceo/api/breeds/image/random").openConnection();
            final JsonObject body = Constants.GSON.fromJson(new InputStreamReader(connection.getInputStream()),
                JsonObject.class);
            final String url = body.get("message").getAsString();
            event.deferReply().setContent(url).mentionRepliedUser(false).queue();
        } catch (final IOException exception) {
            exception.printStackTrace();
            event.deferReply(true).setContent("❌ There has been an issue gathering this dog image.")
                .mentionRepliedUser(false).queue();
        }
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> DOGE = (event, cmd) -> {
        final SubredditReference subreddit = RedditUtils.getSubreddit("Doge");
        final RootCommentNode post = RedditUtils.findValidPost(subreddit, "Doge");
        final String mediaURL = post.getSubject().getUrl().isBlank() ? post.getSubject().getThumbnail()
            : post.getSubject().getUrl();
        event.deferReply().setContent(mediaURL).mentionRepliedUser(false).queue();
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> DUCK = (event, cmd) -> {
        try {
            final URLConnection connection = new URL("https://random-d.uk/api/v2/random").openConnection();
            final JsonObject result = Constants.GSON.fromJson(new InputStreamReader(connection.getInputStream()),
                JsonObject.class);
            final String url = result.get("url").getAsString();
            event.deferReply().setContent(url).mentionRepliedUser(false).queue();
        } catch (final IOException exception) {
            exception.printStackTrace();
            event.deferReply(true).setContent("❌ There has been a problem gathering this duck image!")
                .mentionRepliedUser(false).queue();
        }
    };
    
    private static final BiConsumer<SlashCommandInteractionEvent, ImageCommandType> SNAKE = (event, cmd) -> {
        try {
            final Document page = Jsoup.connect("https://generatorfun.com/random-snake-image").get();
            event.deferReply().setContent(page.select("img").first().attr("abs:src")).mentionRepliedUser(false).queue();
        } catch (final IOException exception) {
            exception.printStackTrace();
            event.deferReply(true).setContent("❌ There has been an issue gathering this snake image.")
                .mentionRepliedUser(false).queue();
        }
    };
    
    static {
        pexels("bee", 3);
        pexels("bird", 3);
        IMAGE_CMD_TYPES.register("blep", new ImageCommandType(BLEP));
        IMAGE_CMD_TYPES.register("bunny", new ImageCommandType(BUNNY));
        IMAGE_CMD_TYPES.register("catbomb", new ImageCommandType(CAT_BOMB));
        IMAGE_CMD_TYPES.register("cat", new ImageCommandType(CAT));
        pexels("cow", 3);
        pexels("crab", 1);
        IMAGE_CMD_TYPES.register("dogbomb", new ImageCommandType(DOG_BOMB));
        IMAGE_CMD_TYPES.register("dog", new ImageCommandType(DOG));
        IMAGE_CMD_TYPES.register("doge", new ImageCommandType(DOGE));
        pexels("dolphin", 3);
        IMAGE_CMD_TYPES.register("duck", new ImageCommandType(DUCK));
        pexels("elephant", 3);
        IMAGE_CMD_TYPES.register("foodporn", new PexelsImageCommandType(3, "food"));
        pexels("forest", 3);
        pexels("fox", 3);
        pexels("giraffe", 3);
        pexels("gorilla", 1);
        pexels("horse", 3);
        pexels("insect", 3);
        pexels("jellyfish", 3);
        pexels("koala", 2);
        pexels("lion", 3);
        pexels("monkey", 3);
        pexels("nature", 3);
        pexels("owl", 3);
        pexels("panda", 3);
        pexels("pig", 2);
        pexels("racoon", 3);
        pexels("sheep", 3);
        IMAGE_CMD_TYPES.register("snake", new ImageCommandType(SNAKE));
        pexels("space", 3);
        pexels("spider", 2);
        pexels("squirrel", 3);
        pexels("tiger", 3);
        pexels("turtle", 3);
        pexels("whale", 3);
        pexels("wolf", 2);
        pexels("zebra", 2);
    }
    
    private static ImageCommandType pexels(String name, int maxPages) {
        return IMAGE_CMD_TYPES.register(name, new PexelsImageCommandType(maxPages));
    }

    public static Registry<ImageCommandType> getImageCommandTypes() {
        return IMAGE_CMD_TYPES;
    }
}
