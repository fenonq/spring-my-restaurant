package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
