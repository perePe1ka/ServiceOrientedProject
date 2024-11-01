package ru.vladuss.serviceorientedproject.services.dto;

import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseDto {
    private String uuid;

    public BaseDto() {
    }

    public BaseDto(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

