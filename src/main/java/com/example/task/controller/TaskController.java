package com.example.task.controller;

import com.example.task.entities.TaskEntity;
import com.example.task.service.TaskService;
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

    @GetMapping("/tasks/{id}")
    public TaskEntity getOne(@PathVariable UUID id) {
        return taskService.getOneTask(id);
    }

    @GetMapping("/tasks")
    public List<TaskEntity> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping("/task")
    public TaskEntity addTask (@RequestBody TaskEntity task) {
        return taskService.addTask(task);
    }

    @PutMapping("/task/{id}/terminated")
    public TaskEntity taskTerminated(@PathVariable UUID id) {
        return taskService.taskTerminated(id);
    }
}
