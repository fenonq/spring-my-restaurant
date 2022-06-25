package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Category;
import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.repositories.CategoryRepository;
import com.fenonq.spring.myrestaurant.repositories.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DishServiceImplTest {

    private static final long ID = 1L;

    @Mock
    DishRepository dishRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    DishServiceImpl dishService;

    @Test
    void testFindAll() {
        Dish dish = Dish.builder().id(ID).build();

        Set<Dish> dishesData = new HashSet<>();
        dishesData.add(dish);

        when(dishRepository.findAll()).thenReturn(dishesData);

        Set<Dish> categories = dishService.findAll();
        assertEquals(1, categories.size());

        verify(dishRepository).findAll();
    }

    @Test
    void testFindById() {
        Dish dish = Dish.builder().id(ID).build();
        Optional<Dish> dishOptional = Optional.of(dish);

        when(dishRepository.findById(anyLong())).thenReturn(dishOptional);

        Dish dishReturned = dishService.findById(ID);

        assertNotNull(dishReturned);
        verify(dishRepository).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Dish> dishOptional = Optional.empty();

        when(dishRepository.findById(anyLong())).thenReturn(dishOptional);

        Dish dishReturned = dishService.findById(ID);

        assertNull(dishReturned);
        verify(dishRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        Dish dish = Dish.builder().id(ID).build();

        when(dishRepository.save(any())).thenReturn(dish);

        assertEquals(dish, dishService.save(dish));
        verify(dishRepository).save(any());
    }

    @Test
    void testDelete() {
        dishService.delete(any());
        verify(dishRepository).delete(any());
    }

    @Test
    void testDeleteById() {
        dishService.deleteById(anyLong());
        verify(dishRepository).deleteById(anyLong());
    }

    @Test
    void testFindAllPageable() {
        Dish dish = Dish.builder().id(ID).build();

        List<Dish> dishesData = new ArrayList<>();
        dishesData.add(dish);

        Page<Dish> dishes = new PageImpl<>(dishesData);

        when(dishRepository.findAll(any())).thenReturn(dishes);

        Page<Dish> dishesReturned = dishService.findAll(any());

        assertNotNull(dishesReturned);
        verify(dishRepository).findAll(any());
    }

    @Test
    void testFindAllByCategoryPageable() {
        Dish dish = Dish.builder().id(ID).build();
        Category category = Category.builder().id(ID).build();
        Optional<Category> categoryOptional = Optional.of(category);

        List<Dish> dishesData = new ArrayList<>();
        dishesData.add(dish);

        Page<Dish> dishes = new PageImpl<>(dishesData);

        when(dishRepository.findAllByCategory(any(), any())).thenReturn(dishes);
        when(categoryRepository.findById(anyLong())).thenReturn(categoryOptional);

        Page<Dish> dishesReturned = dishService.findAllByCategory(ID, any());

        assertNotNull(dishesReturned);
        verify(dishRepository).findAllByCategory(any(), any());
    }

    @Test
    void testChangeVisibility() {
        Dish dish = Dish.builder().id(ID).build();

        dishService.changeVisibility(dish);
        assertEquals(dish.isVisible(), Boolean.FALSE);

        dishService.changeVisibility(dish);
        assertEquals(dish.isVisible(), Boolean.TRUE);
    }
}