package com.fenonq.spring.myrestaurant.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Embeddable
public class LocalizedId implements Serializable {

    private Long id;
    private String locale;

    public LocalizedId(String locale) {
        this.locale = locale;
    }

}
