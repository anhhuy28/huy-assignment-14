package com.coderscampus.Assignment14.repository;

import com.coderscampus.Assignment14.domain.Chat;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ChatRepository {
    private final List<Chat> messages = new CopyOnWriteArrayList<>();
    private final AtomicLong idIncrement = new AtomicLong();

    public Chat save(Chat message) {
        if (message.getId() == null) {
            message.setId(idIncrement.incrementAndGet());
        }
        messages.add(message);
        return message;
    }

    public List<Chat> findByChannelId(Long channelId) {
        if (channelId == null) {
            return List.of(); // Return an empty list if channelId is null
        }
        return messages.stream()
                .filter(m -> m.getChannelId().equals(channelId))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        messages.removeIf(message -> message.getId().equals(id));
    }
}
