package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Music.PlayerManager;

public class Play implements Command {
    @Override
    public void handle(CommandContext ctx) {
        PlayerManager.getInstance().loadAndPlay(ctx.getTextChannel(), ctx.getArg(0));
        ctx.sendMsg(ctx.getArg(0));
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return " help";
    }
}
