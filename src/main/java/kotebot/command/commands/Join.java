package kotebot.command.commands;

import kotebot.command.Command;
import kotebot.command.CommandContext;
import kotebot.Config;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class Join implements Command {
    @Override
    public void handle(CommandContext ctx) {
        AudioChannel audioChannel = ctx.getVoiceChannel();

        if (audioChannel == ctx.getBotChannel()) {
            ctx.sendMsg("**" + Config.get("bot_name") + "** is already in **" + audioChannel.getName() + "**.");
            return;
        }

        if (audioChannel == null) {
            ctx.sendMsg("You need to be in any voice channel.");
            return;
        }

        AudioManager audioManager = ctx.getGuild().getAudioManager();

        audioManager.openAudioConnection(audioChannel);
        ctx.sendMsg( "Connect to **" + audioChannel.getName() + "**.");
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "join`\n**" + Config.get("bot_name") + "** joins your voice channel.\n" +
                "You need to be in any voice channel.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`" + Config.get("prefix")).append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("j");
    }
}
