package com.example.task.service;

import com.example.task.data.InMemoryData;
import com.example.task.entities.TaskEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TaskService {
    private final InMemoryData inMemoryData;

    public TaskService(InMemoryData inMemoryData) {
        this.inMemoryData = inMemoryData;
    }

    public TaskEntity addTask(String description) {
        TaskEntity task = new TaskEntity(description);
        task.setDescription(description);
        task.setState("En cours");
        inMemoryData.addTask(task);
        return task;
    }

    public ResponseEntity<Void> deleteTask(UUID id) {
        try {
            inMemoryData.deleteTask(getOneTask(id));
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<TaskEntity> getTasks() {
        return inMemoryData.getTaskList();
    }

    public TaskEntity getOneTask(UUID id) {
        return inMemoryData.getOne(id);
    }

    public TaskEntity taskTerminated(UUID id) {
        TaskEntity task = inMemoryData.getOne(id);
        assert task.getState().equals("En cours");
        task.setState("Terminée");
        return task;
    }
}
