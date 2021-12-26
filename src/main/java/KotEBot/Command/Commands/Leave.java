package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Leave implements Command {
    @Override
    public void handle(CommandContext ctx) {
        AudioChannel audioChannel = ctx.getChannel();
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
        return "`!leave` : Bot leaves your voice channel.";
    }
}
