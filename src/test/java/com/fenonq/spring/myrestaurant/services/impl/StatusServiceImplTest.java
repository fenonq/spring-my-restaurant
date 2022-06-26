package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.Status;
import com.fenonq.spring.myrestaurant.repositories.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class StatusServiceImplTest {

    private static final long ID = 1L;

    @Mock
    StatusRepository statusRepository;

    @InjectMocks
    StatusServiceImpl statusService;

    @Test
    void testFindAll() {
        Status status = Status.builder().id(ID).build();

        Set<Status> statusesData = new HashSet<>();
        statusesData.add(status);

        when(statusRepository.findAll()).thenReturn(statusesData);

        Set<Status> statuses = statusService.findAll();
        assertEquals(1, statuses.size());

        verify(statusRepository).findAll();
    }

    @Test
    void testFindById() {
        Status status = Status.builder().id(ID).build();
        Optional<Status> statusOptional = Optional.of(status);

        when(statusRepository.findById(anyLong())).thenReturn(statusOptional);

        Status statusReturned = statusService.findById(ID);

        assertNotNull(statusReturned);
        verify(statusRepository).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Status> statusOptional = Optional.empty();

        when(statusRepository.findById(anyLong())).thenReturn(statusOptional);

        Status statusReturned = statusService.findById(ID);

        assertNull(statusReturned);
        verify(statusRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        Status status = Status.builder().id(ID).build();

        when(statusRepository.save(any())).thenReturn(status);

        assertEquals(status, statusService.save(status));
        verify(statusRepository).save(any());
    }

    @Test
    void testDelete() {
        statusService.delete(any());
        verify(statusRepository).delete(any());
    }

    @Test
    void testDeleteById() {
        statusService.deleteById(anyLong());
        verify(statusRepository).deleteById(anyLong());
    }

    @Test
    void testFindByName() {
        Status status = Status.builder().id(ID).build();

        when(statusRepository.findByName(anyString())).thenReturn(status);

        Status statusReturned = statusService.findByName("test");

        assertNotNull(statusReturned);
        verify(statusRepository).findByName(anyString());
    }
}