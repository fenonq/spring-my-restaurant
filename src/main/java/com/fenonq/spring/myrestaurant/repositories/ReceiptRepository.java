package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.Receipt;
import com.fenonq.spring.myrestaurant.model.Status;
import com.fenonq.spring.myrestaurant.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
    Set<Receipt> findByManager(User manager);

    Set<Receipt> findByStatus(Status status);
}
