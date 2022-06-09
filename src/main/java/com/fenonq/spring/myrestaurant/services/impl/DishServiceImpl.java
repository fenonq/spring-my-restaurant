package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.repositories.DishRepository;
import com.fenonq.spring.myrestaurant.services.DishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Set<Dish> findAll() {
        Set<Dish> dishes = new HashSet<>();
        dishRepository.findAll().forEach(dishes::add);
        return dishes;
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish save(Dish object) {
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
        return dishRepository.findAll(pageable);
    }
}
