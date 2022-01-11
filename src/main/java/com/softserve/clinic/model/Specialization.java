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
        name = "specializations",
        uniqueConstraints = {
                @UniqueConstraint(name = "specializations_name_key", columnNames = "name")
        })
@Getter
@Setter
public class Specialization {
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
    private String description;

    @ManyToMany(mappedBy = "specializations")
    private Set<Doctor> doctor = new HashSet<>();

}
