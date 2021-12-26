package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;

public class Stop implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (!ctx.getEvent().getMember().getVoiceState().inAudioChannel()) {
            ctx.sendMsg("You need to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();

        ctx.sendMsg("The player has been stopped and the queue has been cleared");
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "`!stop` : Stop the current song and clear the queue.";
    }
}
