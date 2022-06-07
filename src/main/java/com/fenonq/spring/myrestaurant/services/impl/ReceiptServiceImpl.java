package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Receipt;
import com.fenonq.spring.myrestaurant.repositories.ReceiptRepository;
import com.fenonq.spring.myrestaurant.services.ReceiptService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public Set<Receipt> findAll() {
        Set<Receipt> receipts = new HashSet<>();
        receiptRepository.findAll().forEach(receipts::add);
        return receipts;
    }

    @Override
    public Receipt findById(Long id) {
        return receiptRepository.findById(id).orElse(null);
    }

    @Override
    public Receipt save(Receipt object) {
        return receiptRepository.save(object);
    }

    @Override
    public void delete(Receipt object) {
        receiptRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        receiptRepository.deleteById(id);
    }
}
