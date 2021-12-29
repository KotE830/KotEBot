package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import KotEBot.Config;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class Join implements Command {
    @Override
    public void handle(CommandContext ctx) {
        AudioChannel audioChannel = ctx.getVoiceChannel();

        if (audioChannel == null) {
            ctx.sendMsg("You need to be in any voice channel.");
            return;
        }

        if (audioChannel == ctx.getBotChannel()) {
            ctx.sendMsg(Config.get("bot_name") + "is already in " + audioChannel.getName() + ".");
            return;
        }

        AudioManager audioManager = ctx.getGuild().getAudioManager();

        audioManager.openAudioConnection(audioChannel);
        ctx.sendMsg( "Connect to " + audioChannel.getName() + ".");
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`" + Config.get("prefix") + "join` : Bot joins your voice channel.\n" +
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
