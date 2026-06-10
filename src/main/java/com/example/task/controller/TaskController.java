package com.example.task.controller;

import com.example.task.dto.TaskRequest;
import com.example.task.entities.TaskEntity;
import com.example.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Welcome to the Task Manager API!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getOneTask(id));
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask (@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.addTask(request.description()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/terminated")
    public ResponseEntity<TaskEntity> taskTerminated(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.taskTerminated(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        return taskService.deleteTask(id);
    }
}
