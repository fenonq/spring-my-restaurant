package com.fenonq.spring.myrestaurant.model;

import com.fenonq.spring.myrestaurant.model.enums.Roles;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Builder
    public User(Long id, String name, String login, String password, boolean active) {
        super(id);

        this.username = login;
        this.password = password;
        this.active = active;
        this.name = name;
    }

    @Pattern(regexp = "^([А-ЯІЄЇ][а-яієї]{1,23}|[A-Z][a-z]{1,23})$")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^([А-ЯІЄЇ][а-яієї]{1,23}|[A-Z][a-z]{1,23})$")
    @Column(name = "surname")
    private String surname;

    @Pattern(regexp = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$")
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active = true;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "customer_cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> cart = new ArrayList<>();

}
