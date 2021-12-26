package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;

public class Stop implements Command {
    @Override
    public void handle(CommandContext ctx) {
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return " help";
    }
}
