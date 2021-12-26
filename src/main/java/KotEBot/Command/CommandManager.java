package KotEBot.Command;

import KotEBot.Command.Commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new Help(this));
        addCommand(new Join());
        addCommand(new Leave());
        addCommand(new Pause());
        addCommand(new Ping());
        addCommand(new Play());
        addCommand(new Queue());
        addCommand(new Remove());
        addCommand(new Skip());
        addCommand(new Stop());
    }

    private void addCommand(Command cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Nullable
    public Command getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (Command cmd : this.commands) {
            if (cmd.getName().equals(search) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        // print not command
        return null;
    }

    public void handle(MessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote("!"), "")
                .split("\\s+");
        
        String invoke = split[0].toLowerCase();
        Command cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
