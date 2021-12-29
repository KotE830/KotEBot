package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Config;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Play implements Command {
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

        if (ctx.getArgs().isEmpty()) {
            ctx.sendMsg(Config.get("prefix") + "play [youtube url]");
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) {
            ctx.sendMsg("youtube search : `" + link + "`");
            link = "ytsearch:" + link;
        } else {
            ctx.sendMsg(link);
        }

        PlayerManager.getInstance().loadAndPlay(ctx.getEvent(), link);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "play [youtube url]`\nPlay youtube.\n\nAliase\n" +
                "You need to be in any voice channel with " + Config.get("bot_name") + ".\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
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
    public List<String> getAliases() {
        return Arrays.asList("p");
    }
}
