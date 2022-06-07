package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Category;
import com.fenonq.spring.myrestaurant.repositories.CategoryRepository;
import com.fenonq.spring.myrestaurant.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> findAll() {
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category object) {
        return categoryRepository.save(object);
    }

    @Override
    public void delete(Category object) {
        categoryRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
