package com.example.task.service;

import com.example.task.data.InMemoryData;
import com.example.task.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final InMemoryData inMemoryData;
    private final TaskEntity taskEntity;

    public TaskService(
            InMemoryData inMemoryData,
            TaskEntity taskEntity) {
        this.inMemoryData = inMemoryData;
        this.taskEntity = taskEntity;
    }

    public TaskEntity addTask(TaskEntity task) {
        task.setState("En cours");
        inMemoryData.addTask(task);
        return task;
    }

    public void deleteTask(UUID id) {
        inMemoryData.deleteTask(inMemoryData.getOne(id));
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
        taskEntity.setState("Terminée");
        return task;
    }
}
