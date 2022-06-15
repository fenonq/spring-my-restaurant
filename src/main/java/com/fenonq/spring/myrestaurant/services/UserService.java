package com.fenonq.spring.myrestaurant.services;

import com.fenonq.spring.myrestaurant.model.User;

public interface UserService extends CrudService<User, Long> {
//    void clearUserCart(Long userId);

    User findUserByUsername(String username);
}
