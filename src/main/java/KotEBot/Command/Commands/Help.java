package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Command.CommandManager;

import java.util.Arrays;
import java.util.List;

public class Help implements Command {
    private final CommandManager manager;

    public Help(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();

        if (args.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            manager.getCommands().stream().map(Command::getName).forEach(
                    (it) -> builder.append("`!").append(it).append("`\n")
            );

            ctx.sendMsg("Commands", "https://github.com/KotE830/KotEBot/tree/main/src/main/java/KotEBot/Command/Commands", builder.toString());
            return;
        }

        String search = args.get(0);
        Command command = manager.getCommand(search);

        if (command == null) {
            ctx.sendMsg("Nothing found for `" + search + "`.");
            return;
        }

        ctx.sendMsg(command.getHelp());
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`!help` : List of commands.\n" +
                "`!help [command]` : Manual of the command.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`!").append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist");
    }
}