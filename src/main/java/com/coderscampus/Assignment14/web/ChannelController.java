package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Chat;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.ChatService;
import com.coderscampus.Assignment14.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    public String channel(@PathVariable Long channelId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        User user = userService.getUserById(userId);
        Channel channel = channelService.getChannelById(channelId);
        model.addAttribute("user", user);
        model.addAttribute("channel", channel);
        return "channel";
    }

    @PostMapping("/{channelId}/messages")
    @ResponseBody
    public ResponseEntity<Chat> sendMessage(@PathVariable Long channelId, @RequestBody Chat message, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        message.setUser(user);
        message.setChannelId(channelId);
        Chat savedMessage = chatService.createMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{channelId}/messages")
    @ResponseBody
    public ResponseEntity<List<Chat>> getMessages(@PathVariable Long channelId) {
        List<Chat> messages = chatService.getMessagesByChannelId(channelId);
        return ResponseEntity.ok(messages);
    }
}