package com.softserve.clinic.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String first_name;
    private String second_name;
    private String email;
    private String contact_number;

    public User(int id, String username, String password, String first_name, String second_name, String email, String contact_number) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.contact_number = contact_number;
    }

    public User() {
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.contact_number = contact_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
}
