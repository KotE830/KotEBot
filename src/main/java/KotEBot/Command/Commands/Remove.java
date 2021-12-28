package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;

import java.util.Arrays;
import java.util.List;

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
        StringBuilder builder = new StringBuilder();

        builder.append(" help\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`!").append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("r", "rm");
    }
}
