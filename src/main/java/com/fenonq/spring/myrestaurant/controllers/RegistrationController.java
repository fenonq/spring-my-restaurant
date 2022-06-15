package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.model.enums.Roles;
import com.fenonq.spring.myrestaurant.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "account/registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult result) {
        User userFromDB = userService.findUserByUsername(user.getUsername());

        if (result.hasErrors()) {
            return "account/registration";
        }

        if (userFromDB != null) {
            return "redirect:/registration?error";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Roles.CUSTOMER));
        userService.save(user);
        return "redirect:/login";
    }

}
