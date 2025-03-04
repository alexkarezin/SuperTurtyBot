package dev.darealturtywurty.superturtybot.commands.music;

import dev.darealturtywurty.superturtybot.core.command.CommandCategory;
import dev.darealturtywurty.superturtybot.core.command.CoreCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends CoreCommand {
    public LeaveCommand() {
        super(new Types(true, false, false, false));
    }

    @Override
    public String getAccess() {
        return "Moderators, Singular Person in VC";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public String getDescription() {
        return "Leaves the current vc";
    }

    @Override
    public String getName() {
        return "leavevc";
    }

    @Override
    public String getRichName() {
        return "Leave VC";
    }

    @Override
    public boolean isServerOnly() {
        return true;
    }

    @Override
    protected void runSlash(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild() || event.getGuild() == null || event.getMember() == null) {
            event.deferReply(true).setContent("❌ I must be in a server for you to be able to use this command!")
                    .mentionRepliedUser(false).queue();
            return;
        }

        AudioManager audioManager = event.getGuild().getAudioManager();
        if (!audioManager.isConnected() || audioManager.getConnectedChannel() == null) {
            event.deferReply(true).setContent("❌ I must be in a voice channel for you to be able to use this command!")
                    .mentionRepliedUser(false).queue();
            return;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
        if (memberVoiceState == null || !memberVoiceState.inAudioChannel()) {
            event.deferReply(true)
                    .setContent("❌ You must be in a voice channel for you to be able to use this command!")
                    .mentionRepliedUser(false).queue();
            return;
        }

        AudioChannel channel = audioManager.getConnectedChannel();
        if (memberVoiceState.getChannel() == null || memberVoiceState.getChannel().getIdLong() != channel.getIdLong()) {
            event.deferReply(true)
                    .setContent("❌ You must be in the same voice channel as me for you to be able to use this command!")
                    .mentionRepliedUser(false).queue();
            return;
        }

        if (!event.getMember().hasPermission(channel, Permission.MANAGE_CHANNEL) && channel.getMembers().size() > 2) {
            event.deferReply(true).setContent(
                            "❌ You must be a moderator or the only person in the vc for you to be able to use this command!")
                    .mentionRepliedUser(false).queue();
            return;
        }

        audioManager.closeAudioConnection();
        event.deferReply().setContent("✅ I have left " + channel.getAsMention() + "!").mentionRepliedUser(false)
                .queue();
    }
}
