package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.command.CommandManager;
import kotebot.Config;

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
                    (it) -> builder.append("`").append(Config.get("prefix")).append(it).append("`\n")
            );

            builder.append("\n`" + Config.get("prefix") + "help [command]`");

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

        builder.append("`" + Config.get("prefix") + "help`\nList of commands.\n" +
                "`" + Config.get("prefix") + "help [command]` : Manual of the command.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix") + it + "` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist");
    }
}