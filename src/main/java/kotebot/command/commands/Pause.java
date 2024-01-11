package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import kotebot.music.GuildMusicManager;
import kotebot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class Pause implements Command {
    @Override
    public void handle(CommandContext ctx) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
            ctx.sendMsg("You need to be in voice channel where **" + Config.get("bot_name") + "** is in.");
            return;
        }

        if (audioPlayer.isPaused()) {
            ctx.sendMsg("The player is already paused.");
            return;
        }

        musicManager.scheduler.playerPause(audioPlayer);

        ctx.sendMsg("The player has been paused.");
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp() {
        return "`" + Config.get("prefix") + "pause`\nPause the player.\n" +
                "You need to be in voice channel where **" + Config.get("bot_name") + "** is in.";
    }
}