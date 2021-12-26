package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Music.PlayerManager;

import java.net.URI;
import java.net.URISyntaxException;

public class Play implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) {
            ctx.sendMsg("!play [youtube url]");
            return;
        }

        if (!ctx.getEvent().getMember().getVoiceState().inAudioChannel()) {
            ctx.sendMsg("You need to be in any voice channel.");
            return;
        }

        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(ctx.getTextChannel(), link);
        ctx.sendMsg(link);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "`!play [youtube url]` : Play youtube.";
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
