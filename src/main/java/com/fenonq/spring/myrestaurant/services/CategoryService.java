package com.fenonq.spring.myrestaurant.services;

import com.fenonq.spring.myrestaurant.model.Category;

public interface CategoryService extends CrudService<Category, Long> {
    Category changeVisibility(Category category);
}
