package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import net.dv8tion.jda.api.JDA;

public class Ping implements Command {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx
                        .sendMsg("Reset ping: " + ping + "ms\nWS ping : " + jda.getGatewayPing() + "ms")
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "`" + Config.get("prefix") + "ping`\nPing.";
    }
}
