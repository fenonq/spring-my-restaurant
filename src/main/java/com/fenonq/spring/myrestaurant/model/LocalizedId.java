package com.fenonq.spring.myrestaurant.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class LocalizedId implements Serializable {

    private Long id;
    private Locale locale;

    public LocalizedId(Locale locale) {
        this.locale = locale;
    }

}
