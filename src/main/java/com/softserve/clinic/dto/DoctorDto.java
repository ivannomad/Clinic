package com.softserve.clinic.dto;

import java.util.UUID;

public class DoctorDto {

    public DoctorDto(UUID id) {
        this.id = id;
    }

    private UUID id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
