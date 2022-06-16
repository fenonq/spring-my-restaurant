package com.fenonq.spring.myrestaurant.model.enums;

public enum Roles {
    CUSTOMER,
    MANAGER,
    ADMIN;

    public static Roles changeRole(Roles role) {
        return role == CUSTOMER ? MANAGER : CUSTOMER;
    }
}
