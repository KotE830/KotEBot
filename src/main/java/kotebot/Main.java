package kotebot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;


public class Main {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault(Config.get("token")).build();
        jda.getPresence().setStatus(OnlineStatus.OFFLINE);
        jda.getPresence().setActivity(Activity.listening(Config.get("prefix") + "help"));

        jda.addEventListener(new Listener());
    }
}

