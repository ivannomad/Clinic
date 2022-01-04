package com.softserve.clinic.model;

public class Doctor extends User {
    private String specialization;

    public Doctor(int id, String username, String password, String first_name, String second_name, String email, String contact_number, Specialization specialization) {
        super(id, username, password, first_name, second_name, email, contact_number);
        this.specialization = specialization.getSpecialization();
    }

    public Doctor(Specialization specialization) {
        super();
        this.specialization = specialization.getSpecialization();
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
