package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Status;
import com.fenonq.spring.myrestaurant.repositories.StatusRepository;
import com.fenonq.spring.myrestaurant.services.StatusService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Set<Status> findAll() {
        Set<Status> statuses = new TreeSet<>(Comparator.comparing(Status::getId));
        statusRepository.findAll().forEach(statuses::add);
        return statuses;
    }

    @Override
    public Status findById(Long id) {
        return statusRepository.findById(id).orElse(null);
    }

    @Override
    public Status save(Status object) {
        return statusRepository.save(object);
    }

    @Override
    public void delete(Status object) {
        statusRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        statusRepository.deleteById(id);
    }

    @Override
    public Status findByName(String name) {
        String n = name.substring(0, 1).toUpperCase() + name.substring(1);
        return statusRepository.findByName(n);
    }
}
