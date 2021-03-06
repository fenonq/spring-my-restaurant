package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.dto.LocalizedCategoriesCreationDto;
import com.fenonq.spring.myrestaurant.model.Category;
import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.model.LocalizedCategory;
import com.fenonq.spring.myrestaurant.model.LocalizedId;
import com.fenonq.spring.myrestaurant.services.CategoryService;
import com.fenonq.spring.myrestaurant.services.DishService;
import com.fenonq.spring.myrestaurant.utils.Constants;
import com.fenonq.spring.myrestaurant.utils.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final DishService dishService;

    public CategoryController(CategoryService categoryService, DishService dishService) {
        this.categoryService = categoryService;
        this.dishService = dishService;
    }

    @GetMapping("/categories")
    public String showAllDishes(Model model) {
        log.info("Showing categories...");
        model.addAttribute("categories", categoryService.findAll());
        return "category/show-categories";
    }

    @PostMapping("/changeVisibility/category/{categoryId}")
    public String changeVisibility(@PathVariable Long categoryId) {
        log.info("Changing category visibility");
        Category category = categoryService.findById(categoryId);
        categoryService.changeVisibility(category);
        Set<Dish> categoryDishes = category.getDishes();
        categoryDishes.forEach(dishService::changeVisibility);
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/new/category")
    public String newCategoryForm(Model model) {
        log.info("Creating category");
        model.addAttribute("category", Category.builder().build());

        LocalizedCategoriesCreationDto localizedCategories = new LocalizedCategoriesCreationDto();
        for (int i = 0; i < Constants.languages.length; i++) {
            localizedCategories.addLocalizedCategory(new LocalizedCategory());
        }
        model.addAttribute("form", localizedCategories);
        model.addAttribute("localizedCategoryList", localizedCategories.getLocalizedCategoryList());

        return "category/category-form";
    }

    @GetMapping("/update/category/{id}")
    public String updateCategory(@PathVariable Long id, Model model) {
        log.info("Updating category");
        Category category = categoryService.findById(id);

        model.addAttribute("category", category);

        LocalizedCategoriesCreationDto localizedCategories = new LocalizedCategoriesCreationDto();
        for (LocalizedCategory localizedCategory : category.getLocalizations().values()) {
            localizedCategories.addLocalizedCategory(localizedCategory);
        }

        model.addAttribute("form", localizedCategories);
        model.addAttribute("localizedCategoryList", localizedCategories.getLocalizedCategoryList());
        model.addAttribute("encoder", Base64.class);

        return "category/category-form";
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

        log.info("Category was created/updated");
        return "redirect:/menu";
    }
}
