package com.softserve.clinic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@PrimaryKeyJoinColumn(
        name = "user_id",
        foreignKey = @ForeignKey(name = "doctors_user_id_fkey")
)
public class Doctor extends User {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "doctor_hospital",
            joinColumns = @JoinColumn(
                    name = "doctor_id",
                    foreignKey = @ForeignKey(name = "doctor_hospital_doctor_id_fkey")),
            inverseJoinColumns = @JoinColumn(
                    name = "hospital_id",
                    foreignKey = @ForeignKey(name = "doctor_hospital_hospital_id_fkey"))
    )
    private Set<Hospital> hospitals = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "doctor_specialization",
            joinColumns = @JoinColumn(name = "doctor_id",
                    foreignKey = @ForeignKey(name = "doctor_specialization_doctor_id_fkey")),
            inverseJoinColumns = @JoinColumn(name = "specialization_id",
                    foreignKey = @ForeignKey(name = "doctor_specialization_specialization_id_fkey"))
    )
    private Set<Specialization> specializations = new HashSet<>();

}
