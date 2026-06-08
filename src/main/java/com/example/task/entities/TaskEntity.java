package com.example.task.entities;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskEntity {
    private UUID id;
    private String description;
    private String state;


    /// GETTER ///

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    /// SETTER ///

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(String state) {
        this.state = state;
    }
}
