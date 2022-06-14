package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.dto.LocalizedCategoriesCreationDto;
import com.fenonq.spring.myrestaurant.dto.LocalizedDishesCreationDto;
import com.fenonq.spring.myrestaurant.model.*;
import com.fenonq.spring.myrestaurant.services.CategoryService;
import com.fenonq.spring.myrestaurant.services.DishService;
import com.fenonq.spring.myrestaurant.utils.Constants;
import com.fenonq.spring.myrestaurant.utils.FileUpload;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
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
        model.addAttribute("dish", Dish.builder().build());
        model.addAttribute("categories", categoryService.findAll());

        LocalizedDishesCreationDto localizedDishes = new LocalizedDishesCreationDto();
        for (int i = 0; i < Constants.languages.length; i++) {
            localizedDishes.addLocalizedDish(new LocalizedDish());
        }
        model.addAttribute("form", localizedDishes);
        model.addAttribute("localizedDishList", localizedDishes.getLocalizedDishList());

        return "account/dish-form";
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

        return "account/dish-form";
    }

    @PostMapping("/dish")
    public String createOrUpdateDish(@Valid Dish dish, BindingResult result, LocalizedDishesCreationDto localizedDishes,
                                     Long categoryId, MultipartFile file, Model model) throws IOException {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.error(objectError.toString()));
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

    @GetMapping("/new/category")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", Category.builder().build());

        LocalizedCategoriesCreationDto localizedCategories = new LocalizedCategoriesCreationDto();
        for (int i = 0; i < Constants.languages.length; i++) {
            localizedCategories.addLocalizedCategory(new LocalizedCategory());
        }
        model.addAttribute("form", localizedCategories);
        model.addAttribute("localizedCategoryList", localizedCategories.getLocalizedCategoryList());
        model.addAttribute("encoder", Base64.class);

        return "account/category-form";
    }

    @GetMapping("/update/category/{id}")
    public String updateCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);

        model.addAttribute("category", category);

        LocalizedCategoriesCreationDto localizedCategories = new LocalizedCategoriesCreationDto();
        for (LocalizedCategory localizedCategory : category.getLocalizations().values()) {
            localizedCategories.addLocalizedCategory(localizedCategory);
        }

        model.addAttribute("form", localizedCategories);
        model.addAttribute("localizedCategoryList", localizedCategories.getLocalizedCategoryList());
        model.addAttribute("encoder", Base64.class);

        return "account/category-form";
    }

    @PostMapping("/category")
    public String createOrUpdateCategory(Category category, LocalizedCategoriesCreationDto localizedCategories,
                                         MultipartFile file) throws IOException {
        categoryService.save(category);
        List<LocalizedCategory> localizedCategoryList = new ArrayList<>(localizedCategories.getLocalizedCategoryList());

        if (localizedCategoryList.get(0).getLocalizedId().getLocale() == null) {
            for (int i = 0; i < Constants.languages.length; i++) {
                localizedCategoryList.get(i).setLocalizedId(new LocalizedId(Constants.languages[i]));
            }
        }

        for (LocalizedCategory localizedCategory : localizedCategoryList) {
            category.getLocalizations().put(localizedCategory.getLocalizedId().getLocale(), localizedCategory);
            localizedCategory.setCategory(category);
        }

        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            FileUpload.saveFile(fileName, file);
            category.setImage(fileName);
        }

        categoryService.save(category);

        return "redirect:/menu";
    }

//    private Byte[] getImageBytes(MultipartFile file) throws IOException {
//        Byte[] byteObjects = new Byte[file.getBytes().length];
//        int i = 0;
//        for (byte b : file.getBytes()) {
//            byteObjects[i++] = b;
//        }
//        return byteObjects;
//    }
}
