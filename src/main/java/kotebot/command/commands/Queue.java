package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import kotebot.music.GuildMusicManager;
import kotebot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Queue implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getBotChannel() == null) {
            ctx.sendMsg("**" + Config.get("bot_name") + "** needs to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final LinkedList<AudioTrack> queue = musicManager.scheduler.queue;
        final AudioTrack nowTrack = musicManager.audioPlayer.getPlayingTrack();

        if (queue.isEmpty() && nowTrack == null) {
            ctx.sendMsg("The queue is currently empty.");
            return;
        }

        final AudioTrackInfo nowTrackInfo = nowTrack.getInfo();

        String queueMsg = "**Now Playing**\n";
        queueMsg += nowTrackInfo.title + " [" + formatTime(nowTrack.getDuration()) + "]\n\n";

        final int trackCount = Math.min(queue.size(), 20);
        final List<AudioTrack> trackList = new ArrayList<>(queue);

        queueMsg += "**Next**\n";

        long totalTime = 0;

        for (int i = 0; i < trackCount; i++) {
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            queueMsg += "#" + (i + 1) + " " + info.title + " [" + formatTime(track.getDuration()) + "]\n\n";
            totalTime += track.getDuration();
        }

        if (trackList.size() > trackCount) {
            queueMsg += "And" + (trackList.size() - trackCount) + " more...\n\n";

            for (int i = trackCount; i < trackList.size(); i++) {
                final AudioTrack track = trackList.get(i);
                totalTime += track.getDuration();
            }
        }

        queueMsg += "\n**Total time**\n" + formatTime(totalTime);

        ctx.sendMsg(queueMsg);
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "queue`\nShows the queued up songs.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("q");
    }
}
