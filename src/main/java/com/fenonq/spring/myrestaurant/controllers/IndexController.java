package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final CategoryService categoryService;

    public IndexController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public String welcome(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
