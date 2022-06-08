package com.fenonq.spring.myrestaurant.model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Category extends BaseEntity {

    @Builder
    public Category(Long id, Byte[] image) {
        super(id);
        this.image = image;
    }

    @Lob
    private Byte[] image;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<Dish> dishes = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<Locale, LocalizedCategory> localizations = new HashMap<>();
}
