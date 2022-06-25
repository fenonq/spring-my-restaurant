package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Category;
import com.fenonq.spring.myrestaurant.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {

    private static final long ID = 1L;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void testFindAll() {
        Category category = Category.builder().id(ID).build();

        Set<Category> categoriesData = new HashSet<>();
        categoriesData.add(category);

        when(categoryRepository.findAll()).thenReturn(categoriesData);

        Set<Category> categories = categoryService.findAll();
        assertEquals(1, categories.size());

        verify(categoryRepository).findAll();
    }

    @Test
    void testFindById() {
        Category category = Category.builder().id(ID).build();
        Optional<Category> categoryOptional = Optional.of(category);

        when(categoryRepository.findById(anyLong())).thenReturn(categoryOptional);

        Category categoryReturned = categoryService.findById(ID);

        assertNotNull(categoryReturned);
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Category> categoryOptional = Optional.empty();

        when(categoryRepository.findById(anyLong())).thenReturn(categoryOptional);

        Category categoryReturned = categoryService.findById(ID);

        assertNull(categoryReturned);
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        Category category = Category.builder().id(ID).build();

        when(categoryRepository.save(any())).thenReturn(category);

        assertEquals(category, categoryService.save(category));
        verify(categoryRepository).save(any());
    }

    @Test
    void testDelete() {
        categoryService.delete(any());
        verify(categoryRepository).delete(any());
    }

    @Test
    void testDeleteById() {
        categoryService.deleteById(anyLong());
        verify(categoryRepository).deleteById(anyLong());
    }
}