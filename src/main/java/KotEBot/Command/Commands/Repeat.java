package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;

public class Repeat implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (!ctx.getEvent().getMember().getVoiceState().inAudioChannel()) {
            ctx.sendMsg("You need to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newRepeating = !musicManager.scheduler.repeating;

        musicManager.scheduler.repeating = newRepeating;

        String repeatMsg = newRepeating ? "repeating" : "not repeating";
        ctx.sendMsg("The player has been set to **" + repeatMsg + "**");
    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getHelp() {
        return "`!repeat` : Loops the current song.";
    }
}
