package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.model.enums.Roles;
import com.fenonq.spring.myrestaurant.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Set;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        log.info("Logging in user");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

            return "redirect:/account";
        }
        return "user/login";
    }

    @GetMapping("/registration")
    public String registration() {
        log.info("Creating new user");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

            return "redirect:/account";
        }
        return "user/registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult result) {
        User userFromDB = userService.findUserByUsername(user.getUsername());

        if (result.hasErrors()) {
            return "user/registration";
        }

        if (userFromDB != null) {
            return "redirect:/registration?error";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Roles.CUSTOMER));
        userService.save(user);
        log.info("User was created");
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        log.info("Showing users");
        Set<User> users = userService.findAll();
        users.removeIf(user -> user.getRoles().iterator().next() == Roles.ADMIN);
        model.addAttribute("users", users);
        return "user/show-users";
    }

    @PostMapping("/user/changeRole/{userId}")
    public String changeRole(@PathVariable Long userId) {
        log.info("Changing user role");

        User user = userService.findById(userId);
        userService.save(userService.changeRole(user));

        return "redirect:/users";
    }

    @PostMapping("/user/ban/{userId}")
    public String banUser(@PathVariable Long userId) {
        log.info("Banning user");

        User user = userService.findById(userId);
        if (user.getRoles().iterator().next() != Roles.ADMIN) {
            userService.save(userService.banUser(user));
        }

        return "redirect:/users";
    }
}
