package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.Dish;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Long> {
}
