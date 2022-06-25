package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.repositories.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
}