package com.softserve.clinic.model;

import java.util.Date;

public class Appointment {

    private int id;
    Date date;
    private int doctor_id;
    private int patient_id;
    Doctor doctor;
    Patient patient;


    public Appointment(int id,Doctor doctor,Patient patient) {
        this.id = id;
        this.date = new Date();
        this.doctor=doctor;
        this.patient=patient;
        this.doctor_id = doctor.getId();
        this.patient_id = patient.getId();


    }
    public Appointment() {
        this.id = id;
        this.date = new Date();
        this.doctor=doctor;
        this.patient=patient;
        this.doctor_id = doctor.getId();
        this.patient_id = patient.getId();
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
