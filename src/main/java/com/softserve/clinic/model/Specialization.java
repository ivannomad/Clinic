package com.softserve.clinic.model;

public class Specialization {
    private int id;
    private String specialization;

    public Specialization(int id, String specialization) {
        this.id = id;
        this.specialization = specialization;
    }

    public Specialization() {
        this.id = id;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
