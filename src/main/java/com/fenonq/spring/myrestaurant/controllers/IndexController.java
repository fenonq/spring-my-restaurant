package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    private final CategoryService categoryService;

    public IndexController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public String welcome(Model model) {
        log.info("Showing welcome page...");
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
