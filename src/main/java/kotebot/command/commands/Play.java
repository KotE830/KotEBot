package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import kotebot.music.PlayerManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Play implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getBotChannel() == null) {
            ctx.sendMsg("**" + Config.get("bot_name") + "** needs to be in any voice channel.");
            return;
        }

        if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
            ctx.sendMsg("You need to be in voice channel where **" + Config.get("bot_name") + "** is in.");
            return;
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

        PlayerManager.getInstance().loadAndPlay(ctx.getEvent(), link);
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
