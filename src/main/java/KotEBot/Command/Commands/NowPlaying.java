package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

public class NowPlaying implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (!ctx.getEvent().getMember().getVoiceState().inAudioChannel()) {
            ctx.sendMsg("You need to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        final AudioTrackInfo info = track.getInfo();

        ctx.sendMsg("Now playing `" + info.title + "` by `" + info.author + "` (link: <" + info.uri + ">)");
    }

    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getHelp() {
        return "`!nowplaying` : Shows the current playing song.";
    }
}
