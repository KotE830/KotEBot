package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.command.CommandManager;
import kotebot.Config;
import kotebot.music.PlayerManager;
import net.dv8tion.jda.api.entities.AudioChannel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Play implements Command {
    @Override
    public void handle(CommandContext ctx) {
        AudioChannel botChannel = ctx.getBotChannel();

        if (botChannel == null) {
            CommandManager manager = new CommandManager();
            Command cmd = manager.getCommand("join");
            cmd.handle(ctx);
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.sendMsg("`" + Config.get("prefix") + "play [youtube url]`");
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) {
            ctx.sendMsg("youtube search : **" + link + "**");
            link = "ytsearch:" + link;
        } else {
            ctx.sendMsg(link);
        }

        PlayerManager.getInstance().loadAndPlay(ctx.getEvent(), link, 0);
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "play [youtube url]`\nPlay youtube.\n" +
                "You need to be in voice channel where **" + Config.get("bot_name") + "** is in.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("p");
    }
}
