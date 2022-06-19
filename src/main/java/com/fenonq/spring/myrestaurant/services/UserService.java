package com.fenonq.spring.myrestaurant.services;

import com.fenonq.spring.myrestaurant.model.User;

public interface UserService extends CrudService<User, Long> {
    void clearUserCart(User user);

    User findUserByUsername(String username);

    User changeRole(User user);

    User banUser(User user);
}
