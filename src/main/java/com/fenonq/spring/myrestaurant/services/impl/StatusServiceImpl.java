package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Status;
import com.fenonq.spring.myrestaurant.repositories.StatusRepository;
import com.fenonq.spring.myrestaurant.services.StatusService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Set<Status> findAll() {
        Set<Status> statuses = new HashSet<>();
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
}
