package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.model.enums.Roles;
import com.fenonq.spring.myrestaurant.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        Set<User> users = userService.findAll();
        users.removeIf(user -> user.getRoles().iterator().next() == Roles.ADMIN);
        model.addAttribute("users", users);
        return "account/users";
    }

    @PostMapping("/admin/user/changeRole")
    public String changeRole(Long userId) {

        User user = userService.findById(userId);
        userService.save(userService.changeRole(user));

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/user/ban")
    public String banUser(Long userId) {

        User user = userService.findById(userId);
        userService.save(userService.banUser(user));

        return "redirect:/admin/users";
    }
}
