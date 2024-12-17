package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
        initializeChannels();
    }
    private void initializeChannels() {
        if (getAllChannels().isEmpty()) {
            Channel channel1 = new Channel(null, "Channel One", new ArrayList<>());
            createChannel(channel1);

            Channel channel2 = new Channel(null, "Channel Two", new ArrayList<>());
            createChannel(channel2);

        }
    }
    public Channel createChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    public Channel getChannelById(Long id) {
        return channelRepository.findById(id);
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

}