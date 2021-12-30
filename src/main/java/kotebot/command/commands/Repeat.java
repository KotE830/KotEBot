package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import kotebot.music.GuildMusicManager;
import kotebot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.Arrays;
import java.util.List;

public class Repeat implements Command {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getBotChannel() == null) {
            ctx.sendMsg("**" + Config.get("bot_name") + "** needs to be in any voice channel.");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioTrack track = musicManager.audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        if (ctx.getVoiceChannel() != ctx.getBotChannel()) {
            ctx.sendMsg("You need to be in voice channel where **" + Config.get("bot_name") + "** is in.");
            return;
        }

        final boolean newRepeating = !musicManager.scheduler.repeating;

        musicManager.scheduler.repeating = newRepeating;

        String repeatMsg = newRepeating ? "repeating" : "not repeating";
        ctx.sendMsg("The player has been set to **" + repeatMsg + "**.");
    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "repeat`\nLoops the current queue.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("rp");
    }
}
