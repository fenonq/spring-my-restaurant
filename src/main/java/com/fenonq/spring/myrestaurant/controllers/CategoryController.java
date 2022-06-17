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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String showAllDishes(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/show-categories";
    }

    @PostMapping("/changeVisibility/category/{categoryId}")
    public String changeVisibility(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        categoryService.changeVisibility(category);
        categoryService.save(category);
        return "redirect:/categories";
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

        return "category/category-form";
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

        return "redirect:/menu";
    }
}
