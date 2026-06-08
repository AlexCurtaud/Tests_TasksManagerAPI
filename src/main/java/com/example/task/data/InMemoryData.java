package com.example.task.data;

import com.example.task.entities.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class InMemoryData {
    private List<TaskEntity> taskList = new ArrayList<>();

    public TaskEntity getOne(UUID id) {
        return getTaskList().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst().orElseThrow();
    }

    public List<TaskEntity> getTaskList() {
        return taskList;
    }

    public void addTask(TaskEntity task) {
        taskList.add(task);
    }

    public void deleteTask(TaskEntity task) {
        taskList.remove(task);
    }
}
