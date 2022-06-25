package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.model.enums.Roles;
import com.fenonq.spring.myrestaurant.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private static final long ID = 1L;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testFindAll() {
        User user = User.builder().id(ID).build();

        Set<User> usersData = new HashSet<>();
        usersData.add(user);

        when(userRepository.findAll()).thenReturn(usersData);

        Set<User> users = userService.findAll();
        assertEquals(1, users.size());

        verify(userRepository).findAll();
    }

    @Test
    void testFindById() {
        User user = User.builder().id(ID).build();
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        User userReturned = userService.findById(ID);

        assertNotNull(userReturned);
        verify(userRepository).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<User> userOptional = Optional.empty();

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        User userReturned = userService.findById(ID);

        assertNull(userReturned);
        verify(userRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        User user = User.builder().id(ID).build();

        when(userRepository.save(any())).thenReturn(user);

        assertEquals(user, userService.save(user));
        verify(userRepository).save(any());
    }

    @Test
    void testDelete() {
        userService.delete(any());
        verify(userRepository).delete(any());
    }

    @Test
    void testDeleteById() {
        userService.deleteById(anyLong());
        verify(userRepository).deleteById(anyLong());
    }

    @Test
    void testClearUserCart() {
        User user = User.builder().id(ID).build();
        user.getCart().add(Dish.builder().id(ID).build());

        assertNotEquals(0, user.getCart().size());

        userService.clearUserCart(user);

        assertEquals(0, user.getCart().size());
    }

    @Test
    void testFindUserByUsername() {
        User user = User.builder().id(ID).build();

        when(userRepository.findUserByUsername(anyString())).thenReturn(user);

        User userReturned = userService.findUserByUsername(anyString());

        assertNotNull(userReturned);
    }

    @Test
    void testChangeRole() {
        User user = User.builder().id(ID).build();
        user.getRoles().add(Roles.CUSTOMER);

        userService.changeRole(user);

        assertEquals(Roles.MANAGER, user.getRoles().iterator().next());

        userService.changeRole(user);

        assertEquals(Roles.CUSTOMER, user.getRoles().iterator().next());
    }

    @Test
    void testBanUser() {
        User user = User.builder().id(ID).active(true).build();

        userService.banUser(user);

        assertEquals(Boolean.FALSE, user.isActive());

        userService.banUser(user);

        assertEquals(Boolean.TRUE, user.isActive());
    }
}