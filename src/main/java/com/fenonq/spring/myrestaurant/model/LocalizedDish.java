package com.fenonq.spring.myrestaurant.model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "dish")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedDish {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "name")
    private String name;
}
