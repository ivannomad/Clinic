package com.softserve.clinic.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Setter
@Getter
public class Appointment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dateAndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

}