package kotebot.music;

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

    public void loadAndPlay(MessageReceivedEvent event, String trackUrl, int index) {
        TextChannel channel = event.getTextChannel();
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                if (index == 1) {
                    musicManager.scheduler.queueFirst(track);
                } else {
                    musicManager.scheduler.queue(track);
                }

                sendAddMsg(event, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

                int i = 0;
/*
                StringBuilder builder = new StringBuilder();

                tracks.stream().forEach(
                        (it) -> builder.append(it.getInfo().title + "\n")
                );

                sendMsg(event, builder.toString());*/


                AudioTrack track = tracks.get(i);


                if (index == 1) {
                    musicManager.scheduler.queueFirst(track);
                } else {
                    musicManager.scheduler.queue(track);
                }

                sendAddMsg(event, track);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public void sendAddMsg(MessageReceivedEvent event, AudioTrack track) {
        event.getChannel().sendTyping().queue();

        EmbedBuilder info = new EmbedBuilder();
        info.setTitle(track.getInfo().title, track.getInfo().uri);

        info.setDescription("Adding to queue.\n\n**Total** " + getMusicManager(event.getGuild()).scheduler.queue.size());

        info.setColor(0xf45642);
        info.setFooter("create by " + event.getAuthor().getName(), event.getMember().getUser().getAvatarUrl());

        event.getChannel().sendMessageEmbeds(info.build()).queue();
        info.clear();
    }
}
