package com.fenonq.spring.myrestaurant.model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "category", callSuper = true)
@Entity
public class Dish extends BaseEntity {

    @Builder
    public Dish(Long id, int price, int weight, String image, Category category) {
        super(id);
        this.price = price;
        this.weight = weight;
        this.image = image;
        this.category = category;
    }

    @Min(1)
    @Max(999)
    @Column(name = "price")
    private int price;

    @Min(1)
    @Max(9999)
    @Column(name = "weight")
    private int weight;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "dish", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<Locale, LocalizedDish> localizations = new HashMap<>();

}
