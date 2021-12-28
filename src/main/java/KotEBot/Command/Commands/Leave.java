package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.List;

public class Leave implements Command {
    @Override
    public void handle(CommandContext ctx) {
        AudioChannel audioChannel = ctx.getVoiceChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();

        audioManager.closeAudioConnection();
        ctx.sendMsg( "Disconnect to " + audioChannel.getName());
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("`!leave` : Bot leaves your voice channel.\n\nAliase\n");

        this.getAliases().stream().forEach(
                (it) -> builder.append("`!").append(it).append("` ")
        );

        return builder.toString();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("l");
    }
}
