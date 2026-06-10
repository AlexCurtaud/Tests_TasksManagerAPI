package com.example.task.entities;

import org.springframework.stereotype.Component;

import java.util.UUID;

public class TaskEntity {
    private UUID id;
    private String description;
    private String state;

    public TaskEntity(String description) {
        this.description = description;
        this.id = UUID.randomUUID();
        this.state = "En cours";
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

//    public void setId(UUID id) {
//        this.id = id;
//    }

    public void setState(String state) {
        this.state = state;
    }
}
