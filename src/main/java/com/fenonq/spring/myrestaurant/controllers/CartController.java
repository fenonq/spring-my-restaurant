package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.services.DishService;
import com.fenonq.spring.myrestaurant.services.ReceiptService;
import com.fenonq.spring.myrestaurant.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class CartController {

    private final UserService userService;
    private final DishService dishService;

    public CartController(UserService userService, DishService dishService) {
        this.userService = userService;
        this.dishService = dishService;
    }

    @GetMapping("/cart/user")
    public String showUserCart(Authentication authentication, Model model) {
        User user = userService.findUserByUsername(authentication.getName());
        List<Dish> userCatList = user.getCart();

        Map<Dish, Long> userCart = new TreeMap<>(Comparator.comparing(Dish::getId));

        userCart.putAll(userCatList
                .stream()
                .collect(Collectors.groupingBy(dish -> dish, Collectors.counting())));

        model.addAttribute("userCart", userCart);
        return "cart/user-cart";
    }

    @PostMapping("/cart/add/{dishId}")
    public String changeCountOfDishes(@PathVariable Long dishId, Authentication authentication) {
        User user = userService.findUserByUsername(authentication.getName());
        Dish dish = dishService.findById(dishId);

        user.getCart().add(dish);

        userService.save(user);
        return "redirect:/cart/user";
    }

    @PostMapping("/cart/remove/{dishId}")
    public String removeDishFromUserCart(@PathVariable Long dishId, Authentication authentication) {
        User user = userService.findUserByUsername(authentication.getName());

        user.getCart().remove(dishService.findById(dishId));

        userService.save(user);
        return "redirect:/cart/user";
    }
}
