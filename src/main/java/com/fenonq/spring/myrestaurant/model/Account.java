package com.fenonq.spring.myrestaurant.model;

import com.fenonq.spring.myrestaurant.model.enums.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Account extends BaseEntity {

    @Builder
    public Account(Long id, String name, String surname, String login, String password, Roles role) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Roles role;

    @ManyToMany
    @JoinTable(name = "customer_cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> cart = new ArrayList<>();

}
