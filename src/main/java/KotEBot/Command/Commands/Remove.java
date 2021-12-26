package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;

public class Remove implements Command {
    @Override
    public void handle(CommandContext ctx) {
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getHelp() {
        return " help";
    }
}
