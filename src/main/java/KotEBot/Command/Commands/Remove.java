package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Config;
import KotEBot.Music.GuildMusicManager;
import KotEBot.Music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Remove implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getBotChannel() == null) {
            ctx.sendMsg(Config.get("bot_name") + " needs to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
            ctx.sendMsg("You need to be in voice channel where " + Config.get("bot_name") + " is in.");
            return;
        }

        if (!isNumeric(ctx.getArgs().get(0))) {
            ctx.sendMsg("`" + Config.get("prefix") + "remove [number]`");
            return;
        }

        int index = Integer.parseInt(ctx.getArgs().get(0));
        final LinkedList<AudioTrack> queue = musicManager.scheduler.queue;

        if (index > queue.size()) {
            ctx.sendMsg("There are `" + queue.size() + "` songs.");
            return;
        }

        musicManager.scheduler.playerRemove(index - 1);

        ctx.sendMsg("Remove ");
    }

    private boolean isNumeric(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
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
