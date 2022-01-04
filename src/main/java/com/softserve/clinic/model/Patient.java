package com.softserve.clinic.model;

import java.util.Date;

public class Patient extends User {
    private Date date = new Date();


    public Patient(int id, String username, String password, String first_name, String second_name, String email, String contact_number, Date date) {
        super(id, username, password, first_name, second_name, email, contact_number);
        this.date = date;
    }

    public Patient(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
