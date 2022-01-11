package com.softserve.clinic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@PrimaryKeyJoinColumn(
        name = "user_id",
        foreignKey = @ForeignKey(name = "patients_user_id_fkey")
)
public class Patient extends User {

    @Column(nullable = false)
    private LocalDate birthDate;

}
