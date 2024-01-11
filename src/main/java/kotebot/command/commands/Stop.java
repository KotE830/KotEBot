package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import kotebot.music.GuildMusicManager;
import kotebot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class Stop implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
            ctx.sendMsg("You need to be in voice channel where **" + Config.get("bot_name") + "** is in.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioTrack track = musicManager.audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();

        ctx.sendMsg("The player has been stopped and the queue has been cleared.");
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "`" + Config.get("prefix") + "stop`\nStop the current song and clear the queue.\n" +
                "You need to be in voice channel where **" + Config.get("bot_name") + "** is in.";
    }
}
