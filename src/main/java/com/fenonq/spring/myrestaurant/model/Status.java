package com.fenonq.spring.myrestaurant.model;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Status extends BaseEntity {

    @Builder
    public Status(Long id, Byte[] image) {
        super(id);
        this.image = image;
    }

    @Lob
    private Byte[] image;

    @OneToMany(mappedBy = "status", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<Receipt> receipts = new HashSet<>();

    @OneToMany(mappedBy = "status", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @MapKey(name = "localizedId.locale")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<Locale, LocalizedStatus> localizations = new HashMap<>();
}
