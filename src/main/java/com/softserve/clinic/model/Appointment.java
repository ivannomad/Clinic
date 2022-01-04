package com.softserve.clinic.model;

import java.util.Date;

public class Appointment {

    private int id;
    Date date=new Date();
    private int doctor_id;
    private int patient_id;


    public Appointment(int id, Date date, int doctor_id, int patient_id) {
        this.id = id;
        this.date = date;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
    }
    public Appointment() {
        this.id = id;
        this.date = date;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
}
