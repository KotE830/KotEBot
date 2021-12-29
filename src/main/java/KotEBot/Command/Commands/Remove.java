package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Config;

import java.util.Arrays;
import java.util.List;

public class Remove implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getBotChannel() == null) {
            ctx.sendMsg(Config.get("bot_name") + " needs to be in any voice channel.");
            return;
        }

        if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
            ctx.sendMsg("You need to be in voice channel where " + Config.get("bot_name") + " is in.");
            return;
        }
/*
        if (queue.isEmpty()) {
            ctx.sendMsg("The queue is currently empty.");
            return;
        }*/
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "help\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("r", "rm");
    }
}
