package com.fenonq.spring.myrestaurant.dto;

import com.fenonq.spring.myrestaurant.model.LocalizedCategory;
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
public class LocalizedCategoriesCreationDto {
    private List<LocalizedCategory> localizedCategoryList = new ArrayList<>();

    public void addLocalizedCategory(LocalizedCategory localizedCategory) {
        this.localizedCategoryList.add(localizedCategory);
    }

}
