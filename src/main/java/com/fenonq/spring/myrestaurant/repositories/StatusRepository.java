package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Long> {
}
