package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Config;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class Leave implements Command {
    @Override
    public void handle(CommandContext ctx) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioTrack track = musicManager.audioPlayer.getPlayingTrack();

        if (track != null) {
            if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
                ctx.sendMsg("The music is playing.\nYou need to be in voice channel where " + Config.get("bot_name") + " is in.");
                return;
            }

            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
        }

        AudioChannel audioChannel = ctx.getGuild().getMemberById(Config.get("bot_id")).getVoiceState().getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();

        audioManager.closeAudioConnection();
        ctx.sendMsg( "Disconnect to " + audioChannel.getName() + ".");
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "leave`\nBot leaves the voice channel.\n" +
                "If music is playing, stop the current song and clear the queue.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("l");
    }
}
