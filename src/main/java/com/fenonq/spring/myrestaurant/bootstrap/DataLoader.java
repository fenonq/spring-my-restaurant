package com.fenonq.spring.myrestaurant.bootstrap;

import com.fenonq.spring.myrestaurant.model.*;
import com.fenonq.spring.myrestaurant.model.enums.Roles;
import com.fenonq.spring.myrestaurant.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Locale;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryService categoryService;
    private final StatusService statusService;
    private final UserService userService;

    public DataLoader(CategoryService categoryService, StatusService statusService, UserService userService) {
        this.categoryService = categoryService;
        this.statusService = statusService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        log.info("Data is loading...");
        Locale ua = Locale.forLanguageTag("uk-UA");
        Locale en = Locale.UK;

        Status status =
                Status
                        .builder()
                        .image("new.png")
                        .build();

        LocalizedStatus localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("New")
                        .status(status)
                        .build();
        status.getLocalizations().put(en, localizedStatus1);

        LocalizedStatus localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Новий")
                        .status(status)
                        .build();
        status.getLocalizations().put(ua, localizedStatus2);

        statusService.save(status);

        status =
                Status
                        .builder()
                        .image("accepted.png")
                        .build();

        localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Accepted")
                        .status(status)
                        .build();
        status.getLocalizations().put(en, localizedStatus1);

        localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Прийнято")
                        .status(status)
                        .build();
        status.getLocalizations().put(ua, localizedStatus2);

        statusService.save(status);

        status =
                Status
                        .builder()
                        .image("cooking.png")
                        .build();

        localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Cooking")
                        .status(status)
                        .build();
        status.getLocalizations().put(en, localizedStatus1);

        localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Готується")
                        .status(status)
                        .build();
        status.getLocalizations().put(ua, localizedStatus2);

        statusService.save(status);

        status =
                Status
                        .builder()
                        .image("delivering.png")
                        .build();

        localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Delivering")
                        .status(status)
                        .build();
        status.getLocalizations().put(en, localizedStatus1);

        localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Доставляється")
                        .status(status)
                        .build();
        status.getLocalizations().put(ua, localizedStatus2);

        statusService.save(status);

        status =
                Status
                        .builder()
                        .image("done.png")
                        .build();

        localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Done")
                        .status(status)
                        .build();
        status.getLocalizations().put(en, localizedStatus1);

        localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Готово")
                        .status(status)
                        .build();
        status.getLocalizations().put(ua, localizedStatus2);

        statusService.save(status);

        status =
                Status
                        .builder()
                        .image("canceled.png")
                        .build();

        localizedStatus1 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Canceled")
                        .status(status)
                        .build();
        status.getLocalizations().put(en, localizedStatus1);

        localizedStatus2 =
                LocalizedStatus
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Відмінено")
                        .status(status)
                        .build();
        status.getLocalizations().put(ua, localizedStatus2);

        statusService.save(status);

        Category category =
                Category
                        .builder()
                        .image("first-dishes.png")
                        .build();

        LocalizedCategory localizedCategory1 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("First dishes")
                        .category(category)
                        .build();
        category.getLocalizations().put(en, localizedCategory1);

        LocalizedCategory localizedCategory2 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Перші страви")
                        .category(category)
                        .build();
        category.getLocalizations().put(ua, localizedCategory2);

        Category category1 =
                Category
                        .builder()
                        .image("dish-8.png")
                        .build();

        LocalizedCategory localizedCategory3 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Second dishes")
                        .category(category1)
                        .build();
        category1.getLocalizations().put(en, localizedCategory3);

        LocalizedCategory localizedCategory4 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Другі страви")
                        .category(category1)
                        .build();
        category1.getLocalizations().put(ua, localizedCategory4);

        Category category2 =
                Category
                        .builder()
                        .image("salads.png")
                        .build();

        LocalizedCategory localizedCategory5 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Salads")
                        .category(category2)
                        .build();
        category2.getLocalizations().put(en, localizedCategory5);

        LocalizedCategory localizedCategory6 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Салати")
                        .category(category2)
                        .build();
        category2.getLocalizations().put(ua, localizedCategory6);

        Category category3 =
                Category
                        .builder()
                        .image("desserts.png")
                        .build();

        LocalizedCategory localizedCategory7 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Desserts")
                        .category(category3)
                        .build();
        category3.getLocalizations().put(en, localizedCategory7);

        LocalizedCategory localizedCategory8 =
                LocalizedCategory
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Десерти")
                        .category(category3)
                        .build();
        category3.getLocalizations().put(ua, localizedCategory8);

        Dish bograch =
                Dish
                        .builder()
                        .price(119)
                        .weight(350)
                        .image("dish-1.png")
                        .build();

        LocalizedDish localizedDish1 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Bograch")
                        .description("Made from meat, sweet peppers, ground paprika," +
                                " tomatoes, potatoes, carrots and spices")
                        .dish(bograch)
                        .build();
        bograch.getLocalizations().put(en, localizedDish1);

        LocalizedDish localizedDish2 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Бограч")
                        .description("Приготований з м`яса, солодкого перцю, меленої паприки," +
                                " помідорів, картоплі, моркви та спецій")
                        .dish(bograch)
                        .build();
        bograch.getLocalizations().put(ua, localizedDish2);

        category.getDishes().add(bograch);
        bograch.setCategory(category);


        Dish borsch =
                Dish
                        .builder()
                        .price(105)
                        .weight(350)
                        .image("dish-2.png")
                        .build();

        localizedDish1 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Borsch")
                        .description("Made from chopped beets, cabbage with potatoes and various spices")
                        .dish(borsch)
                        .build();
        borsch.getLocalizations().put(en, localizedDish1);

        localizedDish2 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Борщ")
                        .description("Приготований з посічених буряків, капусти з додатком картоплі та різних приправ")
                        .dish(borsch)
                        .build();
        borsch.getLocalizations().put(ua, localizedDish2);

        category.getDishes().add(borsch);
        borsch.setCategory(category);

        Dish cabbageRolls =
                Dish
                        .builder()
                        .price(120)
                        .weight(200)
                        .image("dish-5.png")
                        .build();

        localizedDish1 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Cabbage rolls")
                        .description("Made from chopped beets, cabbage with potatoes and various spices")
                        .dish(cabbageRolls)
                        .build();
        cabbageRolls.getLocalizations().put(en, localizedDish1);

        localizedDish2 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Голубці")
                        .description("Приготовані з листя свіжої капусти та начинки з м`яса та рису")
                        .dish(cabbageRolls)
                        .build();
        cabbageRolls.getLocalizations().put(ua, localizedDish2);

        category1.getDishes().add(cabbageRolls);
        cabbageRolls.setCategory(category1);

        Dish bakedSalmon =
                Dish
                        .builder()
                        .price(250)
                        .weight(200)
                        .image("dish-6.png")
                        .build();

        localizedDish1 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Baked salmon")
                        .description("Salmon baked with vegetables, potatoes and spices")
                        .dish(bakedSalmon)
                        .build();
        bakedSalmon.getLocalizations().put(en, localizedDish1);

        localizedDish2 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Запечений лосось")
                        .description("Лосось запечений з овочами, картоплею і спеціями")
                        .dish(bakedSalmon)
                        .build();
        bakedSalmon.getLocalizations().put(ua, localizedDish2);

        category1.getDishes().add(bakedSalmon);
        bakedSalmon.setCategory(category1);

        Dish greekSalad =
                Dish
                        .builder()
                        .price(102)
                        .weight(250)
                        .image("dish-9.png")
                        .build();

        localizedDish1 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Greek salad")
                        .description("Salad of cucumbers, tomatoes, olives and feta cheese")
                        .dish(greekSalad)
                        .build();
        greekSalad.getLocalizations().put(en, localizedDish1);

        localizedDish2 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Салат Грецький")
                        .description("Салат з огірків, помідорів, маслин та сиру фета")
                        .dish(greekSalad)
                        .build();
        greekSalad.getLocalizations().put(ua, localizedDish2);

        category2.getDishes().add(greekSalad);
        greekSalad.setCategory(category2);

        Dish caesarSalad =
                Dish
                        .builder()
                        .price(150)
                        .weight(250)
                        .image("dish-10.png")
                        .build();

        localizedDish1 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(en))
                        .name("Caesar salad")
                        .description("Salad of chicken, croutons, parmesan cheese, tomatoes and sauce")
                        .dish(caesarSalad)
                        .build();
        caesarSalad.getLocalizations().put(en, localizedDish1);

        localizedDish2 =
                LocalizedDish
                        .builder()
                        .localizedId(new LocalizedId(ua))
                        .name("Салат Цезар")
                        .description("Салат з курки, грінків, сиру пармезан, помідорів і соусу")
                        .dish(caesarSalad)
                        .build();
        caesarSalad.getLocalizations().put(ua, localizedDish2);

        category2.getDishes().add(caesarSalad);
        caesarSalad.setCategory(category2);

        categoryService.save(category);
        categoryService.save(category1);
        categoryService.save(category2);
        categoryService.save(category3);

        User admin =
                User
                        .builder()
                        .name("Admin")
                        .surname("Admin")
                        .active(true)
                        .login("admin")
                        .password("admin")
                        .build();
        admin.setRoles(Collections.singleton(Roles.ADMIN));

        userService.save(admin);

        User customer =
                User
                        .builder()
                        .name("Customer")
                        .surname("Customer")
                        .active(true)
                        .login("customer")
                        .password("customer")
                        .build();
        customer.setRoles(Collections.singleton(Roles.CUSTOMER));

        userService.save(customer);

        User manager =
                User
                        .builder()
                        .name("Manager")
                        .surname("Manager")
                        .active(true)
                        .login("manager")
                        .password("manager")
                        .build();
        manager.setRoles(Collections.singleton(Roles.MANAGER));

        userService.save(manager);

        log.info("Data loaded");
    }
}
