package com.coderscampus.Assignment14.repository;

import com.coderscampus.Assignment14.domain.Channel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

@Repository
public class ChannelRepository {

    private final Map<Long, Channel> channels = new ConcurrentHashMap<>();
    private final AtomicLong idIncrement = new AtomicLong();

    public Channel save(Channel channel) {
        if (channel.getId() == null) {
            channel.setId(idIncrement.incrementAndGet());
        }
        channels.put(channel.getId(), channel);
        return channel;
    }

    public Channel findById(Long id) {
        return channels.get(id);
    }

    public List<Channel> findAll() {
        return new ArrayList<>(channels.values());
    }

    public void delete(Channel channel) {
        channels.remove(channel.getId());
    }
}
