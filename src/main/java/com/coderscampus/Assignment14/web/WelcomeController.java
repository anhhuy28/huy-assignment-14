package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {

    private final UserService userService;
    private final ChannelService channelService;

    @Autowired
    public WelcomeController(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
    }

    @GetMapping("/")
    public String welcome(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
        }
        model.addAttribute("channels", channelService.getAllChannels());
        return "welcome";
    }

    @PostMapping("/user")
    public String createUser(@RequestParam String username, HttpSession session) {
        User user = new User();
        user.setUsername(username);
        user = userService.createUser(user);
        session.setAttribute("userId", user.getId());
        return "redirect:/";
    }
}