package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.services.UserService;
import com.fenonq.spring.myrestaurant.utils.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class GlobalController {

    private final UserService userService;

    public GlobalController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("languages")
    public Locale[] languages() {
        return Constants.languages;
    }

    @ModelAttribute("currentUser")
    public User user(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();

        return userService.findUserByUsername(username);
    }
}
