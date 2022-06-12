package com.fenonq.spring.myrestaurant.dto;

import com.fenonq.spring.myrestaurant.model.LocalizedDish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedDishesCreationDto {
    private List<LocalizedDish> localizedDishList = new ArrayList<>();

    public void addLocalizedDish(LocalizedDish localizedDish) {
        this.localizedDishList.add(localizedDish);
    }

}
