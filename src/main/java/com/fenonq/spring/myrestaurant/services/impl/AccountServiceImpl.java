package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Account;
import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.repositories.AccountRepository;
import com.fenonq.spring.myrestaurant.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Set<Account> findAll() {
        Set<Account> accounts = new HashSet<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account save(Account object) {
        return accountRepository.save(object);
    }

    @Override
    public void delete(Account object) {
        accountRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }
}
