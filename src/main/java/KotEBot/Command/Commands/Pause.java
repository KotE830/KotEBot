package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;

public class Pause implements Command {
    @Override
    public void handle(CommandContext ctx) {
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp() {
        return " help";
    }
}
