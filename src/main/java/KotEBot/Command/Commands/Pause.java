package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Config;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class Pause implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getBotChannel() == null) {
            ctx.sendMsg(Config.get("bot_name") + " needs to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioTrack track = musicManager.audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
            ctx.sendMsg("You need to be in voice channel where " + Config.get("bot_name") + " is in.");
            return;
        }

        musicManager.scheduler.player.stopTrack();

        ctx.sendMsg("The player has been stopped.");
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp() {
        return "`" + Config.get("prefix") + "pause`\nPause the current song.\n" +
                "You need to be in any voice channel with " + Config.get("bot_name") + ".";
    }
}