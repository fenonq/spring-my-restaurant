package com.fenonq.spring.myrestaurant.model;

import com.fenonq.spring.myrestaurant.model.enums.Roles;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = "receipts")
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Builder
    public User(Long id, String name, String surname, String login, String password, boolean active) {
        super(id);

        this.username = login;
        this.password = password;
        this.active = active;
        this.name = name;
        this.surname = surname;
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

    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    private Set<Receipt> receipts = new HashSet<>();

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
