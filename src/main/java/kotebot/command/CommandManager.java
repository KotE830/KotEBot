package kotebot.command;

import kotebot.command.commands.*;
import kotebot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
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
        addCommand(new NowPlaying());
        addCommand(new Pause());
        addCommand(new Ping());
        addCommand(new Play());
        addCommand(new Queue());
        addCommand(new Remove());
        addCommand(new Repeat());
        addCommand(new Resume());
        addCommand(new Skip());
        addCommand(new Stop());
    }

    private void addCommand(Command cmd) {
        boolean nameFound = this.commands.stream().anyMatch(
                (it) -> it.getName().equalsIgnoreCase(cmd.getName())
        );

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present.");
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

        return null;
    }

    public void handle(MessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("prefix")), "")
                .split("\\s+");
        
        String invoke = split[0].toLowerCase();
        Command cmd = this.getCommand(invoke);

        if (cmd != null) {
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        } else {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle(Config.get("bot_name"), "https://github.com/KotE830/KotEBot");
            info.setDescription("There is no commands.\nYou can check the commands by `" + Config.get("prefix") + "help`");
            info.setColor(0xf45642);
            info.setFooter("create by " + event.getAuthor().getName(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessageEmbeds(info.build()).queue();
            info.clear();
        }
    }
}
