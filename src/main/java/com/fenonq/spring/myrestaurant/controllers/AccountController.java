package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.dto.LocalizedDishesCreationDto;
import com.fenonq.spring.myrestaurant.model.Category;
import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.model.LocalizedDish;
import com.fenonq.spring.myrestaurant.model.LocalizedId;
import com.fenonq.spring.myrestaurant.services.CategoryService;
import com.fenonq.spring.myrestaurant.services.DishService;
import com.fenonq.spring.myrestaurant.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class AccountController {

    private final DishService dishService;
    private final CategoryService categoryService;

    public AccountController(DishService dishService, CategoryService categoryService) {
        this.dishService = dishService;
        this.categoryService = categoryService;
    }

    @GetMapping("/new/dish")
    public String newDishForm(Model model) {
        model.addAttribute("languages", Constants.languages);
        model.addAttribute("dish", Dish.builder().build());
        model.addAttribute("categories", categoryService.findAll());

        LocalizedDishesCreationDto localizedDishes = new LocalizedDishesCreationDto();
        for (int i = 0; i < Constants.languages.length; i++) {
            localizedDishes.addLocalizedDish(new LocalizedDish());
        }
        model.addAttribute("form", localizedDishes);
        model.addAttribute("localizedDishList", localizedDishes.getLocalizedDishList());
        model.addAttribute("encoder", Base64.class);

        return "dish/dish-form";
    }

    @GetMapping("/update/dish/{id}")
    public String updateRecipe(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id);

        model.addAttribute("languages", Constants.languages);
        model.addAttribute("dish", dish);
        model.addAttribute("categories", categoryService.findAll());

        LocalizedDishesCreationDto localizedDishes = new LocalizedDishesCreationDto();
        for (LocalizedDish localizedDish : dish.getLocalizations().values()) {
            localizedDishes.addLocalizedDish(localizedDish);
        }

        model.addAttribute("form", localizedDishes);
        model.addAttribute("localizedDishList", localizedDishes.getLocalizedDishList());
        model.addAttribute("encoder", Base64.class);

        return "dish/dish-form";
    }

    @PostMapping("/dish")
    public String createOrUpdateDish(Dish dish, LocalizedDishesCreationDto localizedDishes, Long categoryId,
                                     MultipartFile file) throws IOException {
        dishService.save(dish);
        List<LocalizedDish> localizedDishList = new ArrayList<>(localizedDishes.getLocalizedDishList());

        localizedDishList.forEach(System.out::println);
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
            dish.setImage(getImageBytes(file));
        }

        categoryService.save(category);
        dishService.save(dish);

        return "redirect:/menu";
    }

    private Byte[] getImageBytes(MultipartFile file) throws IOException {
        Byte[] byteObjects = new Byte[file.getBytes().length];
        int i = 0;
        for (byte b : file.getBytes()) {
            byteObjects[i++] = b;
        }
        return byteObjects;
    }
}
