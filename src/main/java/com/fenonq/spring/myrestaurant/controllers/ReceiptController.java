package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.model.Dish;
import com.fenonq.spring.myrestaurant.model.Receipt;
import com.fenonq.spring.myrestaurant.model.Status;
import com.fenonq.spring.myrestaurant.model.User;
import com.fenonq.spring.myrestaurant.services.ReceiptService;
import com.fenonq.spring.myrestaurant.services.StatusService;
import com.fenonq.spring.myrestaurant.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ReceiptController {

    private final ReceiptService receiptService;
    private final UserService userService;
    private final StatusService statusService;

    public ReceiptController(ReceiptService receiptService, UserService userService, StatusService statusService) {
        this.receiptService = receiptService;
        this.userService = userService;
        this.statusService = statusService;
    }

    @GetMapping("/receipts")
    public String showReceipts(Model model, Authentication authentication,
                               @RequestParam(required = false) Long statusId) {
        log.info("Showing receipts...");
        User manager = userService.findUserByUsername(authentication.getName());
        Set<Status> statuses = statusService.findAll();
        Set<Receipt> receipts;

        if (statusId == null) {
            statusId = 0L;
        }

        if (statusId == 0L) {
            receipts = receiptService.findAll();
        } else if (statusId == statuses.size() + 1) {
            receipts = receiptService.findByManager(manager);
        } else {
            receipts = receiptService.findByStatus(statusService.findById(statusId));
        }

        Map<Receipt, Map<Dish, Long>> receiptsDishes = new TreeMap<>(Comparator.comparing(Receipt::getId));
        Map<Dish, Long> receiptDishes;

        for (Receipt receipt : receipts) {
            receiptDishes = new TreeMap<>(Comparator.comparing(Dish::getId));
            receiptDishes
                    .putAll(receipt
                            .getDishes()
                            .stream()
                            .collect(Collectors.groupingBy(dish -> dish, Collectors.counting())));

            receiptsDishes.put(receipt, receiptDishes);
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("manager", manager);
        model.addAttribute("receipts", receipts);
        model.addAttribute("receiptsDishes", receiptsDishes);
        return "receipt/show-receipts";
    }

    @PostMapping("/receipt/order")
    public String makeOrder(Authentication authentication) {
        log.info("Creating order");
        User user = userService.findUserByUsername(authentication.getName());
        int totalPrice =
                user.getCart()
                        .stream()
                        .mapToInt(Dish::getPrice)
                        .sum();

        Receipt receipt =
                Receipt
                        .builder()
                        .customer(user)
                        .status(statusService.findById(1L))
                        .totalPrice(totalPrice)
                        .createDate(LocalDateTime.now())
                        .dishes(user.getCart())
                        .build();
        user.getReceipts().add(receipt);

        userService.clearUserCart(user);
        userService.save(user);
        return "redirect:/menu";
    }

    @PostMapping("/receipt/nextStatus/{receiptId}")
    public String nextReceiptStatus(@PathVariable Long receiptId, Authentication authentication) {
        log.info("Changing receipt status");
        User manager = userService.findUserByUsername(authentication.getName());
        Receipt receipt = receiptService.findById(receiptId);

        Status statusDone = statusService.findByName("done");
        Status statusCanceled = statusService.findByName("canceled");

        if (receipt.getManager() == null) {
            receipt.setManager(manager);
        }

        if (statusDone == null || statusCanceled == null) {
            log.error("Status does not exist");
            return "redirect:/receipts";
        }

        if (!receipt.getStatus().getId().equals(statusDone.getId()) &&
                !receipt.getStatus().getId().equals(statusCanceled.getId()) &&
                receipt.getManager().getId().equals(manager.getId())) {

            receiptService.save(receiptService.nextReceiptStatus(receipt));
        }
        return "redirect:/receipts";
    }

    @PostMapping("/receipt/cancel/{receiptId}")
    public String cancelReceipt(@PathVariable Long receiptId, Authentication authentication) {
        log.info("Canceling receipt");
        User manager = userService.findUserByUsername(authentication.getName());
        Receipt receipt = receiptService.findById(receiptId);

        if (receipt.getManager() == null) {
            receipt.setManager(manager);
        }

        if (receipt.getManager().getId().equals(manager.getId())) {
            receiptService.save(receiptService.cancelOrRenewReceipt(receipt));
        }

        return "redirect:/receipts";
    }
}
