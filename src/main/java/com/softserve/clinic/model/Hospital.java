package com.softserve.clinic.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "hospitals",
        uniqueConstraints = {
                @UniqueConstraint(name = "hospitals_name_key", columnNames = "name")
        })
@Getter
@Setter
public class Hospital {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String addressNumber;

    @ManyToMany(mappedBy = "hospitals")
    private Set<Doctor> doctors = new HashSet<>();

}
