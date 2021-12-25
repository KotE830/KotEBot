package KotEBot;

import KotEBot.Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {
    public static JDA jda;
    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault("token").build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.playing("!help"));

        jda.addEventListener(new Main());
    }

    public void printMsg(MessageReceivedEvent event, String str) {
        EmbedBuilder info = new EmbedBuilder();
        info.setTitle("KotEBot", "https://google.com");
        info.setDescription(str);
        info.setColor(0xf45642);
        info.setFooter("create by " + event.getAuthor().getName(), event.getMember().getUser().getAvatarUrl());

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessageEmbeds(info.build()).queue();
        info.clear();
    }

    enum Commands {
            HELP, JOIN, LEAVE, PAUSE, PLAY, QUEUE, REMOVE, SKIP, STOP
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        char prefix = '!';

        String command = args[0].substring(1);

        if (event.getAuthor().isBot())
            return;
        if (args[0].charAt(0) != prefix)
            return;
        if (command.length() == 0) {
            printMsg(event, prefix + " command");
            return;
        }

        switch (command) {
            case "h":
            case "help":
                if (args.length == 1) {
                    printMsg(event, Help.getHelp(-1));
                } else {
                    switch (args[1]) {
                        case "h":
                        case "help":
                            printMsg(event, Help.getHelp(Commands.HELP.ordinal()));
                            break;
                        case "j":
                        case "join":
                            printMsg(event, Help.getHelp(Commands.JOIN.ordinal()));
                            break;
                        case "l":
                        case "leave":
                            printMsg(event, Help.getHelp(Commands.LEAVE.ordinal()));
                            break;
                        case "p":
                        case "play":
                            printMsg(event, Help.getHelp(Commands.PLAY.ordinal()));
                        case "q":
                        case "queue":
                            printMsg(event, Help.getHelp(Commands.QUEUE.ordinal()));
                            break;
                        case "r":
                        case "remove":
                            printMsg(event, Help.getHelp(Commands.REMOVE.ordinal()));
                            break;
                        default:
                            printMsg(event, "There is no command.");
                            break;
                    }
                }
                break;
            case "j":
            case "join":
                AudioChannel audioChannel = event.getMember().getVoiceState().getChannel();
                AudioManager audioManager = event.getGuild().getAudioManager();

                audioManager.openAudioConnection(audioChannel);
                printMsg(event, "Connect to " + audioChannel.getName());
                break;
            case "p":
            case "play":
                PlayerManager.getInstance()
                        .loadAndPlay(event.getTextChannel(), args[1]);
                break;
            case "q":
            case "queue":
                break;
            case "r":
            case "remove":
                break;
            default:
                printMsg(event, "There is no command.");
                break;
        }
    }
}