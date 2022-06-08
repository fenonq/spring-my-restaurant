package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.services.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping({"", "/", "/menu"})
    public String menu(Model model) {
        model.addAttribute("dishes", dishService.findAll());
        model.addAttribute("languages", new Locale[]{Locale.UK,
                Locale.forLanguageTag("uk-UA")});
        return "dish/menu";
    }
}
