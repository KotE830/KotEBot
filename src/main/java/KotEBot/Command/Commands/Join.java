package KotEBot.Command.Commands;

import KotEBot.Command.Command;
import KotEBot.Command.CommandContext;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join implements Command {
    @Override
    public void handle(CommandContext ctx) {
        AudioChannel audioChannel = ctx.getChannel();
        AudioManager audioManager = ctx.getGuild().getAudioManager();

        audioManager.openAudioConnection(audioChannel);
        ctx.sendMsg( "Connect to " + audioChannel.getName());
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "`!join` : Bot joins your voice channel.";
    }
}
