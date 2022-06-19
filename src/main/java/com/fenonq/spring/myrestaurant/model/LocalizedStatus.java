package com.fenonq.spring.myrestaurant.model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "status")
@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedStatus {

    @EmbeddedId
    private LocalizedId localizedId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "status_id")
    private Status status;
}
