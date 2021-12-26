package KotEBot;

import KotEBot.Command.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class Listener extends ListenerAdapter {
    private final CommandManager manager = new CommandManager();

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        User user = event.getAuthor();
        String prefix = "!";

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        String[] args = event.getMessage().getContentRaw().split(" ");
/*
        String command = args[0].substring(1);

        if (command.length() == 0) {
            printMsg(event, prefix + " command");
            return;
        }*/

        String raw = event.getMessage().getContentRaw();

        if (raw.startsWith(prefix)) {
            manager.handle(event);
        }
    }
}
