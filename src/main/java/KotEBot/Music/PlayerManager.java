package KotEBot.Music;

import KotEBot.Config;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(MessageReceivedEvent event, String trackUrl) {
        TextChannel channel = event.getTextChannel();
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);

                sendMsg(event, "Adding to queue:\n`" +
                        track.getInfo().title + "`\nby `" +
                        track.getInfo().author + "`"
                );
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

/*
                StringBuilder builder = new StringBuilder();

                tracks.stream().forEach(
                        (it) -> builder.append(it.getInfo().title + "\n")
                );

                sendMsg(event, builder.toString());*/

                sendMsg(event, "Adding to queue: `" + (tracks.size()) +
                        "` tracks from playlist `" + playlist.getName() + "`"
                );

                musicManager.scheduler.queue(tracks.get(0));
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public void sendMsg(MessageReceivedEvent event, String str) {
        EmbedBuilder info = new EmbedBuilder();
        info.setTitle(Config.get("bot_name"), "https://github.com/KotE830/KotEBot");
        info.setDescription(str);
        info.setColor(0xf45642);
        info.setFooter("create by " + event.getAuthor().getName(), event.getMember().getUser().getAvatarUrl());

        event.getChannel().sendMessageEmbeds(info.build()).queue();
        info.clear();
    }
}
