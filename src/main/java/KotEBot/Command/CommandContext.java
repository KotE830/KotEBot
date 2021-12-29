package KotEBot.Command;

import KotEBot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandContext {
    private final MessageReceivedEvent event;
    private final List<String> args;

    public CommandContext(MessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
    }

    public MessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArgs() {
        return this.args;
    }

    public AudioChannel getVoiceChannel() {
        return this.event.getMember().getVoiceState().getChannel();
    }

    public AudioChannel getBotChannel() {
        return this.event.getGuild().getMemberById(Config.get("bot_id")).getVoiceState().getChannel();
    }

    public Guild getGuild() {
        return this.event.getGuild();
    }

    public JDA getJDA() {
        return this.event.getJDA();
    }


    public void sendMsg(String str) {
        EmbedBuilder info = new EmbedBuilder();
        info.setTitle(Config.get("bot_name"), "https://github.com/KotE830/KotEBot");
        info.setDescription(str);
        info.setColor(0xf45642);
        info.setFooter("create by " + this.event.getAuthor().getName(), this.event.getMember().getUser().getAvatarUrl());

        this.event.getChannel().sendMessageEmbeds(info.build()).queue();
        info.clear();
    }

    public void sendMsg(String title, String titleUrl, String str) {
        EmbedBuilder info = new EmbedBuilder();
        info.setTitle(title, titleUrl);
        info.setDescription(str);
        info.setColor(0xf45642);
        info.setFooter("create by " + this.event.getAuthor().getName(), this.event.getMember().getUser().getAvatarUrl());

        this.event.getChannel().sendMessageEmbeds(info.build()).queue();
        info.clear();
    }
}
