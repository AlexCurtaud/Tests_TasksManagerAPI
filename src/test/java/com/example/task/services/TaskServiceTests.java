package com.example.task.services;

import com.example.task.data.InMemoryData;
import com.example.task.entities.TaskEntity;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskServiceTests {
    private TaskService taskService;
    private InMemoryData inMemoryData;

    @BeforeEach
    void setUp() {
        inMemoryData = new InMemoryData();
        taskService = new TaskService(inMemoryData);
    }

    @Test
    void testAddTask() {
        taskService.addTask("Etape 1");
        assertEquals(1, inMemoryData.getTaskList().size());
    }

    @Test
    void testDeleteTask() {
        TaskEntity task = taskService.addTask("Etape 2");
        taskService.deleteTask(task.getId());
        assertEquals(0, inMemoryData.getTaskList().size());
    }

    @Test
    void testGetTasks() {
        taskService.addTask("Etape 3");
        taskService.addTask("Etape 4");
        String description1 = taskService.getTasks().getFirst().getDescription();
        String description2 = taskService.getTasks().get(1).getDescription();
        assertEquals("Etape 3", description1);
        assertEquals("Etape 4", description2);
    }

    @Test
    void testTaskTerminated() {
        TaskEntity task = taskService.addTask("Etape 5");
        taskService.taskTerminated(task.getId());
        assertEquals("Terminée", task.getState());
    }

    @Test
    void testGetOne() {
        TaskEntity task1 = taskService.addTask("Etape 6");
        TaskEntity task2 = taskService.getOneTask(task1.getId());
        assertEquals(task1.getDescription(), task2.getDescription());
    }
}
