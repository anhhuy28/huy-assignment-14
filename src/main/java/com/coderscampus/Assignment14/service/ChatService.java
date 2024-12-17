package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Chat;
import com.coderscampus.Assignment14.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createMessage(Chat message) {
        return chatRepository.save(message);
    }

    public List<Chat> getMessagesByChannelId(Long channelId) {
        return chatRepository.findByChannelId(channelId);
    }
}
