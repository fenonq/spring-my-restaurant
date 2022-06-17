package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.dto.LocalizedDishesCreationDto;
import com.fenonq.spring.myrestaurant.model.Category;
import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.model.LocalizedDish;
import com.fenonq.spring.myrestaurant.model.LocalizedId;
import com.fenonq.spring.myrestaurant.services.CategoryService;
import com.fenonq.spring.myrestaurant.services.DishService;
import com.fenonq.spring.myrestaurant.utils.Constants;
import com.fenonq.spring.myrestaurant.utils.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class DishController {

    private final DishService dishService;
    private final CategoryService categoryService;

    public DishController(DishService dishService, CategoryService categoryService) {
        this.dishService = dishService;
        this.categoryService = categoryService;
    }

    @GetMapping("/dishes")
    public String showAllDishes(Model model) {
        model.addAttribute("dishes", dishService.findAll());
        return "dish/show-dishes";
    }

    @PostMapping("/changeVisibility/dish/{dishId}")
    public String changeVisibility(@PathVariable Long dishId) {
        Dish dish = dishService.findById(dishId);
        dishService.changeVisibility(dish);
        dishService.save(dish);
        return "redirect:/dishes";
    }

    @GetMapping("/new/dish")
    public String newDishForm(Model model) {
        model.addAttribute("dish", Dish.builder().build());
        model.addAttribute("categories", categoryService.findAll());

        LocalizedDishesCreationDto localizedDishes = new LocalizedDishesCreationDto();
        for (int i = 0; i < Constants.languages.length; i++) {
            localizedDishes.addLocalizedDish(new LocalizedDish());
        }
        model.addAttribute("form", localizedDishes);
        model.addAttribute("localizedDishList", localizedDishes.getLocalizedDishList());

        return "dish/dish-form";
    }

    @GetMapping("/update/dish/{id}")
    public String updateDish(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id);

        model.addAttribute("dish", dish);
        model.addAttribute("categories", categoryService.findAll());

        LocalizedDishesCreationDto localizedDishes = new LocalizedDishesCreationDto();
        for (LocalizedDish localizedDish : dish.getLocalizations().values()) {
            localizedDishes.addLocalizedDish(localizedDish);
        }

        model.addAttribute("form", localizedDishes);
        model.addAttribute("localizedDishList", localizedDishes.getLocalizedDishList());

        return "dish/dish-form";
    }

    @PostMapping("/dish")
    public String createOrUpdateDish(@Valid Dish dish, BindingResult result, LocalizedDishesCreationDto localizedDishes,
                                     Long categoryId, MultipartFile file, Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("dish", dish);
            model.addAttribute("form", localizedDishes);
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("localizedDishList", localizedDishes.getLocalizedDishList());
            return "account/dish-form";
        }

        dishService.save(dish);
        List<LocalizedDish> localizedDishList = new ArrayList<>(localizedDishes.getLocalizedDishList());

        if (localizedDishList.get(0).getLocalizedId().getLocale() == null) {
            for (int i = 0; i < Constants.languages.length; i++) {
                localizedDishList.get(i).setLocalizedId(new LocalizedId(Constants.languages[i]));
            }
        }

        for (LocalizedDish localizedDish : localizedDishList) {
            dish.getLocalizations().put(localizedDish.getLocalizedId().getLocale(), localizedDish);
            localizedDish.setDish(dish);
        }

        Category category = categoryService.findById(categoryId);
        dish.setCategory(category);
        category.getDishes().removeIf(dish1 -> dish1.getId().equals(dish.getId()));
        category.getDishes().add(dish);

        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            FileUpload.saveFile(fileName, file);
            dish.setImage(fileName);
        }

        categoryService.save(category);
        dishService.save(dish);

        return "redirect:/menu";
    }
}
