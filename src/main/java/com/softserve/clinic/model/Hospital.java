package com.softserve.clinic.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hospital {
    @Id
    private Long id;
    private String hospital_name;
    private String city;
    private String street;
    private String adress_number;


    public Hospital(Long id, String hospital_name, String city, String street, String adress_number) {
        this.id = id;
        this.hospital_name = hospital_name;
        this.city = city;
        this.street = street;
        this.adress_number = adress_number;
    }

    public Hospital() {
        this.id = id;
        this.hospital_name = hospital_name;
        this.city = city;
        this.street = street;
        this.adress_number = adress_number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdress_number() {
        return adress_number;
    }

    public void setAdress_number(String adress_number) {
        this.adress_number = adress_number;
    }
}
