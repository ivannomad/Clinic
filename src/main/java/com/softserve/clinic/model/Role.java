package com.softserve.clinic.model;

public class Role {
    private int serial;
    private String role_name;

    public Role(int serial, String role_name) {

    }

    public Role() {
        this.serial = serial;
        this.role_name = role_name;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
