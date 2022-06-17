package com.fenonq.spring.myrestaurant.services;

import com.fenonq.spring.myrestaurant.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DishService extends CrudService<Dish, Long> {
    Page<Dish> findAll(Pageable pageable);

    Page<Dish> findAllByCategory(Long id, Pageable pageable);

    Dish changeVisibility(Dish dish);
}
