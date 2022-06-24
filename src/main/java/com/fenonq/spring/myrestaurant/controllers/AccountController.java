package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.model.Receipt;
import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class AccountController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AccountController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/account")
    public String showAccount(Model model, Authentication authentication) {
        log.info("Showing account...");
        User user = userService.findUserByUsername(authentication.getName());

        Map<Receipt, Map<Dish, Long>> receiptsDishes = new TreeMap<>(Comparator.comparing(Receipt::getId));
        Map<Dish, Long> receiptDishes;

        for (Receipt receipt : user.getReceipts()) {
            receiptDishes = new TreeMap<>(Comparator.comparing(Dish::getId));
            receiptDishes
                    .putAll(receipt
                            .getDishes()
                            .stream()
                            .collect(Collectors.groupingBy(dish -> dish, Collectors.counting())));

            receiptsDishes.put(receipt, receiptDishes);
        }

        model.addAttribute("receiptsDishes", receiptsDishes);
        model.addAttribute("user", user);
        return "account/account";
    }

    @GetMapping("/account/changePassword")
    public String changePasswordForm() {
        log.info("Changing password");
        return "account/change-password-form";
    }

    @PostMapping("/account/changePassword")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Authentication authentication) {

        User user = userService.findUserByUsername(authentication.getName());

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "redirect:/account/changePassword?error";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
        log.info("Password was changed");
        return "redirect:/account";
    }
}
