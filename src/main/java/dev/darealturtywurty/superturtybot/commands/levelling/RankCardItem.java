package dev.darealturtywurty.superturtybot.commands.levelling;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import dev.darealturtywurty.superturtybot.TurtyBot;
import dev.darealturtywurty.superturtybot.core.util.BotUtils;
import dev.darealturtywurty.superturtybot.registry.Registerable;

public class RankCardItem implements Registerable {
    public final String data;
    public final Type type;
    public final Rarity rarity;
    public final BufferedImage thumbnail;
    
    private String name;
    
    public RankCardItem(String data, Type type, Rarity rarity) {
        this(data, type, rarity, thumbnail(data));
    }
    
    public RankCardItem(String data, Type type, Rarity rarity, BufferedImage thumbnail) {
        this.data = data;
        this.type = type;
        this.rarity = rarity;
        this.thumbnail = thumbnail;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Registerable setName(String name) {
        if (this.name == null) {
            this.name = name;
        }
        
        return this;
    }
    
    private static final BufferedImage thumbnail(String name) {
        try {
            return BotUtils
                .resize(ImageIO.read(TurtyBot.class.getResourceAsStream("/levels/thumbnails/" + name + ".png")), 120);
        } catch (final IOException | IllegalArgumentException exception) {
            return null;
        }
    }
    
    public enum Rarity {
        COMMON("common", 0.4f), UNCOMMON("uncommon", 0.3f), RARE("rare", 0.15f), EPIC("epic", 0.1f),
        LEGENDARY("legendary", 0.05f);
        
        public final String saveName;
        public final float chance;
        
        Rarity(String name, float chance) {
            this.saveName = name;
            this.chance = chance;
        }
    }
    
    public enum Type {
        BACKGROUND_IMAGE("bgImg"), OUTLINE_IMAGE("outlineImg"), XP_OUTLINE_IMAGE("xpOutlineImg"),
        XP_EMPTY_IMAGE("xpEmptyImg"), XP_FILL_IMAGE("xpFillImg"), AVATAR_OUTLINE_IMAGE("avatarOutlineImg");
        
        public final String saveName;
        
        Type(String name) {
            this.saveName = name;
        }
    }
}
