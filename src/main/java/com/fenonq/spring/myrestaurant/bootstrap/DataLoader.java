package com.fenonq.spring.myrestaurant.bootstrap;

import com.fenonq.spring.myrestaurant.model.*;
import com.fenonq.spring.myrestaurant.model.enums.Locales;
import com.fenonq.spring.myrestaurant.repositories.CategoryRepository;
import com.fenonq.spring.myrestaurant.repositories.DishRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(DishRepository dishRepository, CategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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

        categoryRepository.save(category);
        dishRepository.save(dish);
    }
}
