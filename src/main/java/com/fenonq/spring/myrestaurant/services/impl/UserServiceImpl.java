package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.model.enums.Roles;
import com.fenonq.spring.myrestaurant.repositories.UserRepository;
import com.fenonq.spring.myrestaurant.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository accountRepository;

    public UserServiceImpl(UserRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Set<User> findAll() {
        Set<User> accounts = new TreeSet<>(Comparator.comparing(User::getId));
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public User findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User object) {
        if (object.getId() == null) {
            object.setPassword(passwordEncoder.encode(object.getPassword()));
        }
        return accountRepository.save(object);
    }

    @Override
    public void delete(User object) {
        accountRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void clearUserCart(User user) {
        user.getCart().clear();
    }

    @Override
    public User findUserByUsername(String username) {
        return accountRepository.findUserByUsername(username);
    }

    @Override
    public User changeRole(User user) {
        Roles previousRole = user.getRoles().iterator().next();
        user.getRoles().clear();
        user.getRoles().add(Roles.changeRole(previousRole));
        return user;
    }

    @Override
    public User banUser(User user) {
        user.setActive(!user.isActive());
        return user;
    }
}
