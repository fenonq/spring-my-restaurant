package com.fenonq.spring.myrestaurant.services;

import com.fenonq.spring.myrestaurant.model.Receipt;
import com.fenonq.spring.myrestaurant.model.Status;
import com.fenonq.spring.myrestaurant.model.User;

import java.util.Set;

public interface ReceiptService extends CrudService<Receipt, Long> {
    Receipt nextReceiptStatus(Receipt receipt);

    Receipt cancelOrRenewReceipt(Receipt receipt);

    Set<Receipt> findByManager(User manager);

    Set<Receipt> findByStatus(Status status);
}
