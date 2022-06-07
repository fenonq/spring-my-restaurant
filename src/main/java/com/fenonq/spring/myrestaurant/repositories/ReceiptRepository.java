package com.fenonq.spring.myrestaurant.repositories;

import com.fenonq.spring.myrestaurant.model.Receipt;
import org.springframework.data.repository.CrudRepository;

public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
}
