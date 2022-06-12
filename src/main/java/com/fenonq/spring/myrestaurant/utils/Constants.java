package com.fenonq.spring.myrestaurant.utils;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Constants {
    public static final Locale[] languages = {
            Locale.UK,
            Locale.forLanguageTag("uk-UA"),
//            Locale.JAPAN
    };

    public static final Map<String, String> dishProperties = Stream.of(new String[][]{
            {"menu.select.orderBy.price", "price"},
            {"menu.select.orderBy.weight", "weight"},
            {"menu.select.orderBy.dish.name", "localizations.name"},
            {"menu.select.orderBy.category.name", "category.localizations.name"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

}
