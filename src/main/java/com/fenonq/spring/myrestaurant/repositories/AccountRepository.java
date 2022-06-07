package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
