package com.fenonq.spring.myrestaurant.services.impl;

import com.fenonq.spring.myrestaurant.model.*;
import com.fenonq.spring.myrestaurant.repositories.ReceiptRepository;
import com.fenonq.spring.myrestaurant.repositories.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReceiptServiceImplTest {

    private static final long ID = 1L;

    @Mock
    ReceiptRepository receiptRepository;

    @Mock
    StatusRepository statusRepository;

    @InjectMocks
    ReceiptServiceImpl receiptService;

    @Test
    void testFindAll() {
        Receipt receipt = Receipt.builder().id(ID).build();

        Set<Receipt> receiptsData = new HashSet<>();
        receiptsData.add(receipt);

        when(receiptRepository.findAll()).thenReturn(receiptsData);

        Set<Receipt> receipts = receiptService.findAll();
        assertEquals(1, receipts.size());

        verify(receiptRepository).findAll();
    }

    @Test
    void testFindById() {
        Receipt receipt = Receipt.builder().id(ID).build();
        Optional<Receipt> receiptOptional = Optional.of(receipt);

        when(receiptRepository.findById(anyLong())).thenReturn(receiptOptional);

        Receipt receiptReturned = receiptService.findById(ID);

        assertNotNull(receiptReturned);
        verify(receiptRepository).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Receipt> receiptOptional = Optional.empty();

        when(receiptRepository.findById(anyLong())).thenReturn(receiptOptional);

        Receipt receiptReturned = receiptService.findById(ID);

        assertNull(receiptReturned);
        verify(receiptRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        Receipt receipt = Receipt.builder().id(ID).build();

        when(receiptRepository.save(any())).thenReturn(receipt);

        assertEquals(receipt, receiptService.save(receipt));
        verify(receiptRepository).save(any());
    }

    @Test
    void testDelete() {
        receiptService.delete(any());
        verify(receiptRepository).delete(any());
    }

    @Test
    void testDeleteById() {
        receiptService.deleteById(anyLong());
        verify(receiptRepository).deleteById(anyLong());
    }

    @Test
    void testNextReceiptStatus() {
        Receipt receipt = Receipt.builder().id(ID).status(Status.builder().id(ID + 1).build()).build();
        Status nextStatus = Status.builder().id(ID).build();
        Optional<Status> optionalNextStatus = Optional.of(nextStatus);

        when(statusRepository.findById(anyLong())).thenReturn(optionalNextStatus);

        Receipt receiptReturned = receiptService.nextReceiptStatus(receipt);

        assertEquals(receiptReturned.getStatus().getId(), ID);
        verify(statusRepository).findById(anyLong());
    }

    @Test
    void testCancelOrRenewReceipt() {
        Locale ua = Locale.forLanguageTag("uk-UA");
        Locale en = Locale.UK;

        Status statusNew =
                Status
                        .builder()
                        .build();

        LocalizedStatus localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("New")
                        .status(statusNew)
                        .build();
        statusNew.getLocalizations().put(en, localizedStatus1);

        LocalizedStatus localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Новий")
                        .status(statusNew)
                        .build();
        statusNew.getLocalizations().put(ua, localizedStatus2);

        Status statusAccepted =
                Status
                        .builder()
                        .build();

        LocalizedStatus localizedStatus3 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Accepted")
                        .status(statusAccepted)
                        .build();
        statusAccepted.getLocalizations().put(en, localizedStatus3);

        LocalizedStatus localizedStatus4 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Прийнято")
                        .status(statusAccepted)
                        .build();
        statusAccepted.getLocalizations().put(ua, localizedStatus4);

        Status statusCanceled =
                Status
                        .builder()
                        .build();

        LocalizedStatus localizedStatus5 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Canceled")
                        .status(statusCanceled)
                        .build();
        statusCanceled.getLocalizations().put(en, localizedStatus5);

        LocalizedStatus localizedStatus6 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Відмінено")
                        .status(statusCanceled)
                        .build();
        statusCanceled.getLocalizations().put(ua, localizedStatus6);

        Receipt receipt = Receipt.builder().id(ID).status(statusNew).build();
        assertEquals(receipt.getStatus(), statusNew);

        when(statusRepository.findByName(anyString())).thenReturn(statusCanceled);

        Receipt receiptReturned = receiptService.cancelOrRenewReceipt(receipt);
        assertEquals(receiptReturned.getStatus(), statusCanceled);

        when(statusRepository.findByName(anyString())).thenReturn(statusAccepted);

        receiptReturned = receiptService.cancelOrRenewReceipt(receipt);
        assertEquals(receiptReturned.getStatus(), statusAccepted);
    }

    @Test
    void testFindByManager() {
        Set<Receipt> receipts = new HashSet<>();
        receipts.add(Receipt.builder().id(ID).build());
        receipts.add(Receipt.builder().id(ID + 1).build());
        receipts.add(Receipt.builder().id(ID + 2).build());

        when(receiptRepository.findByManager(any())).thenReturn(receipts);

        Set<Receipt> receiptsReturned = receiptService.findByManager(User.builder().id(ID).build());

        assertNotNull(receiptsReturned);
        assertEquals(3, receiptsReturned.size());
    }

    @Test
    void testFindByStatus() {
        Set<Receipt> receipts = new HashSet<>();
        receipts.add(Receipt.builder().id(ID).build());
        receipts.add(Receipt.builder().id(ID + 1).build());
        receipts.add(Receipt.builder().id(ID + 2).build());

        when(receiptRepository.findByStatus(any())).thenReturn(receipts);

        Set<Receipt> receiptsReturned = receiptService.findByStatus(Status.builder().id(ID).build());

        assertNotNull(receiptsReturned);
        assertEquals(3, receiptsReturned.size());
    }
}