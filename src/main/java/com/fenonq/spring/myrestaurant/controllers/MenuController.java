package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.services.CategoryService;
import com.fenonq.spring.myrestaurant.services.DishService;
import com.fenonq.spring.myrestaurant.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
public class MenuController {

    private final DishService dishService;
    private final CategoryService categoryService;

    public MenuController(DishService dishService, CategoryService categoryService) {
        this.dishService = dishService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/menu"})
    public String menu(Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, value = 8) Pageable pageable,
                       @RequestParam(required = false) Long categoryId) {
        model.addAttribute("encoder", Base64.class);
        model.addAttribute("languages", Constants.languages);
        model.addAttribute("dishProperties", Constants.dishProperties);
        model.addAttribute("categories", categoryService.findAll());

        Page<Dish> dishes;
        if (categoryId == null || categoryId == 0) {
            dishes = dishService.findAll(pageable);
        } else {
            dishes = dishService.findAllByCategory(categoryId, pageable);
        }
        model.addAttribute("dishes", dishes);

        return "dish/menu";
    }
}
