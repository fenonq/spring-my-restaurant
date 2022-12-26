package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Category;
import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.repositories.CategoryRepository;
import com.fenonq.spring.myrestaurant.repositories.DishRepository;
import com.fenonq.spring.myrestaurant.services.DishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;

    public DishServiceImpl(DishRepository dishRepository, CategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Dish> findAll() {
        Set<Dish> dishes = new TreeSet<>(Comparator.comparing(Dish::getId));
        dishRepository.findAll().forEach(dishes::add);
        return dishes;
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish save(Dish object) {
        if (object.getId() == null) {
            object.setVisible(true);
        }
        return dishRepository.save(object);
    }

    @Override
    public void delete(Dish object) {
        dishRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public Page<Dish> findAll(Pageable pageable) {
        Page<Dish> pageableDishes = dishRepository.findAll(pageable);
        List<Dish> distinctDishes = pageableDishes.stream().distinct().collect(Collectors.toList());
        return new PageImpl<>(distinctDishes, pageable, distinctDishes.size());
    }

    @Override
    public Page<Dish> findAllByCategory(Long id, Pageable pageable) {
        Category category = categoryRepository.findById(id).orElse(null);
        Page<Dish> pageableDishesByCategory = dishRepository.findAllByCategory(category, pageable);
        List<Dish> distinctDishes = pageableDishesByCategory.stream().distinct().collect(Collectors.toList());
        return new PageImpl<>(distinctDishes, pageable, distinctDishes.size());
    }

    @Override
    public Dish changeVisibility(Dish dish) {
        dish.setVisible(!dish.isVisible());
        return dish;
    }
}
