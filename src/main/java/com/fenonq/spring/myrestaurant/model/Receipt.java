package com.fenonq.spring.myrestaurant.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Receipt extends BaseEntity {

    @Builder
    public Receipt(Long id, User customer, User manager, Status status,
                   int totalPrice, LocalDateTime createDate, List<Dish> dishes) {
        super(id);
        this.customer = customer;
        this.manager = manager;
        this.status = status;
        this.totalPrice = totalPrice;
        this.createDate = createDate;

        if (dishes != null) {
            this.dishes = new ArrayList<>(dishes);
        } else {
            this.dishes = new ArrayList<>();
        }
    }

    @ManyToOne
    private User customer;

    @ManyToOne
    private User manager;

    @ManyToOne
    private Status status;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "receipt_has_dish",
            joinColumns = @JoinColumn(name = "receipt_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes = new ArrayList<>();

}
