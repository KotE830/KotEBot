package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

public class Skip implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (!ctx.getEvent().getMember().getVoiceState().inAudioChannel()) {
            ctx.sendMsg("You need to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            ctx.sendMsg("There is no track playing currrently");
            return;
        }

        musicManager.scheduler.nextTrack();
        ctx.sendMsg("Skipped the current track");
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "`!skip` : Skip the current track";
    }
}
