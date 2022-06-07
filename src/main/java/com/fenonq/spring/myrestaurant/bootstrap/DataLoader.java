package com.fenonq.spring.myrestaurant.bootstrap;

import com.fenonq.spring.myrestaurant.model.*;
import com.fenonq.spring.myrestaurant.model.enums.Locales;
import com.fenonq.spring.myrestaurant.model.enums.Roles;
import com.fenonq.spring.myrestaurant.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DataLoader implements CommandLineRunner {

    private final DishService dishService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final ReceiptService receiptService;
    private final StatusService statusService;

    public DataLoader(DishService dishService, CategoryService categoryService,
                      AccountService accountService, ReceiptService receiptService,
                      StatusService statusService) {
        this.dishService = dishService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.receiptService = receiptService;
        this.statusService = statusService;
    }

    @Override
    public void run(String... args) throws Exception {
        Account customer =
                Account
                        .builder()
                        .name("Name")
                        .surname("Surname")
                        .login("customer")
                        .password("1234")
                        .role(Roles.CUSTOMER)
                        .build();

        Account manager =
                Account
                        .builder()
                        .name("Name")
                        .surname("Surname")
                        .login("manager")
                        .password("1234")
                        .role(Roles.MANAGER)
                        .build();

        accountService.save(manager);

        Status status =
                Status
                        .builder()
                        .build();

        LocalizedStatus localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(Locales.EN.toString()))
                        .name("EnStatus")
                        .status(status)
                        .build();
        status.getLocalizations().put(Locales.EN.toString(), localizedStatus1);

        LocalizedStatus localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(Locales.UA.toString()))
                        .name("UaStatus")
                        .status(status)
                        .build();
        status.getLocalizations().put(Locales.UA.toString(), localizedStatus2);

        statusService.save(status);

        Dish dish =
                Dish
                        .builder()
                        .price(123)
                        .weight(321)
                        .build();

        Category category =
                Category
                        .builder()
                        .build();

        LocalizedCategory localizedCategory1 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(Locales.EN.toString()))
                        .name("EnCategory")
                        .category(category)
                        .build();
        category.getLocalizations().put(Locales.EN.toString(), localizedCategory1);

        LocalizedCategory localizedCategory2 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(Locales.UA.toString()))
                        .name("UaCategory")
                        .category(category)
                        .build();
        category.getLocalizations().put(Locales.UA.toString(), localizedCategory2);

        LocalizedDish localizedDish1 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(Locales.EN.toString()))
                        .name("EnDish")
                        .description("EnDesc")
                        .dish(dish)
                        .build();
        dish.getLocalizations().put(Locales.EN.toString(), localizedDish1);

        LocalizedDish localizedDish2 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(Locales.UA.toString()))
                        .name("UaDish")
                        .description("UaDesc")
                        .dish(dish)
                        .build();
        dish.getLocalizations().put(Locales.UA.toString(), localizedDish2);

        category.getDishes().add(dish);
        dish.setCategory(category);

        categoryService.save(category);
        dishService.save(dish);

        customer.getCart().add(dish);
        accountService.save(customer);


        Receipt receipt =
                Receipt
                        .builder()
                        .customer(customer)
                        .manager(manager)
                        .totalPrice(123)
                        .createDate(LocalDateTime.now())
                        .status(status)
                        .build();
        receipt.getDishes().add(dish);
        receipt.getDishes().add(dish);
        receiptService.save(receipt);
    }
}
