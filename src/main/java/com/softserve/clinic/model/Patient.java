package com.softserve.clinic.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@Getter
@Setter
public class Patient extends User {

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany
    @JoinColumn(name = "id")
    private Set<Appointment> appointments;
}