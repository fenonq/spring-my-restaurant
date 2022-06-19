package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Long> {
    @Query("select s from Status s inner join LocalizedStatus ls on s.id = ls.localizedId.id where ls.name = ?1")
    Status findByName(String name);
}
