package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Long> {
    Page<Dish> findAll(Pageable pageable);
}
