package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Config;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import java.util.Arrays;
import java.util.List;

public class NowPlaying implements Command {
    @Override
    public void handle(CommandContext ctx) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        final AudioTrackInfo info = track.getInfo();

        ctx.sendMsg("Now playing `" + info.title + "` by `" + info.author + "\n` (link: <" + info.uri + ">)");
    }

    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "nowplaying` : Shows the current playing song.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("now", "np", "nowplay");
    }
}