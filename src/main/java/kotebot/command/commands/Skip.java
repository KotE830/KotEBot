package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import kotebot.music.GuildMusicManager;
import kotebot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.Arrays;
import java.util.List;

public class Skip implements Command {
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

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioTrack track = musicManager.audioPlayer.getPlayingTrack();

        if (track == null) {
            ctx.sendMsg("There is no track playing currently.");
            return;
        }

        musicManager.scheduler.playerSkip(track);

        ctx.sendMsg("Skipped **" + track.getInfo().title + "**.");
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "skip`\nSkip the current track.\n" +
                "You need to be in voice channel where **" + Config.get("bot_name") + "** is in.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("s");
    }
}
