package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Chat;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.ChatService;
import com.coderscampus.Assignment14.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/{channelId}")
    public String channel(@PathVariable Long channelId, @RequestParam Long userId, Model model) {
        if (channelId == null || userId == null) {
            return "redirect:/"; // Redirect if any required parameter is missing
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/"; // Redirect if user does not exist
        }

        Channel channel = channelService.getChannelById(channelId);
        if (channel == null) {
            return "redirect:/"; // Redirect if channel does not exist
        }

        model.addAttribute("user", user);
        model.addAttribute("channel", channel);
        return "channel";
    }

    @PostMapping("/{channelId}/messages")
    @ResponseBody
    public ResponseEntity<Chat> sendMessage(@PathVariable Long channelId, @RequestBody Chat message, @RequestParam Long userId) {
        if (channelId == null || userId == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        message.setUser(user);
        message.setChannelId(channelId);
        Chat savedMessage = chatService.createMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{channelId}/messages")
    @ResponseBody
    public ResponseEntity<List<Chat>> getMessages(@PathVariable Long channelId) {
        if (channelId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Chat> messages = chatService.getMessagesByChannelId(channelId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Channel>> getAllChannels() {
        List<Channel> channels = channelService.getAllChannels();
        return ResponseEntity.ok(channels);
    }
}
