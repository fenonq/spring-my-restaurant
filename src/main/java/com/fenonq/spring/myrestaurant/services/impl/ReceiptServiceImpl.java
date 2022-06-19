package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Receipt;
import com.fenonq.spring.myrestaurant.model.Status;
import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.repositories.ReceiptRepository;
import com.fenonq.spring.myrestaurant.repositories.StatusRepository;
import com.fenonq.spring.myrestaurant.services.ReceiptService;
import com.fenonq.spring.myrestaurant.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final StatusRepository statusRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, StatusRepository statusRepository) {
        this.receiptRepository = receiptRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Set<Receipt> findAll() {
        Set<Receipt> receipts = new TreeSet<>(Comparator.comparing(Receipt::getId));
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

    @Override
    public Receipt nextReceiptStatus(Receipt receipt) {
        receipt.setStatus(statusRepository.findById(receipt.getStatus().getId() + 1).orElse(null));
        return receipt;
    }

    @Override
    public Receipt cancelOrRenewReceipt(Receipt receipt) {
        Status accepted = statusRepository.findByName("Accepted");
        Status canceled = statusRepository.findByName("Canceled");

        if (!receipt.getStatus().getLocalizations().get(Constants.languages[0]).getName()
                .equals(canceled.getLocalizations().get(Constants.languages[0]).getName())) {
            receipt.setStatus(canceled);
        } else {
            receipt.setStatus(accepted);
        }
        return receipt;
    }

    @Override
    public Set<Receipt> findByManager(User manager) {
        return new HashSet<>(receiptRepository.findByManager(manager));
    }

    @Override
    public Set<Receipt> findByStatus(Status status) {
        return new HashSet<>(receiptRepository.findByStatus(status));
    }
}
