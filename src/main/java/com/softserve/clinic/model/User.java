package com.softserve.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(name = "username", length = 64)
    private String username;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "second_name", length = 64)
    private String secondName;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "contact_number", length = 10)
    private String contactNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String username, String password, String firstName, String secondName, String email, String contactNumber, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.role = role;
    }
}