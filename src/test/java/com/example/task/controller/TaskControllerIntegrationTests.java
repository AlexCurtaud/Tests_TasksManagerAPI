package com.example.task.controller;

import com.example.task.entities.TaskEntity;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService; // real TaskService object automatically injected in TaskController instance

    @Test
    void testGetOne() throws Exception {
        TaskEntity task1 = taskService.addTask("Etape 1");
        TaskEntity task = taskService.getOneTask(task1.getId());

        mockMvc.perform(get("/tasks/"+task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Etape 1"))
                .andExpect(jsonPath("$.description").isString())
                //JSON response send the UUID as a String, this is why I have to add toString()
                .andExpect(jsonPath("$.id").value(task.getId().toString()))
                .andExpect(jsonPath("$.state").value("En cours"));
    }

    @Test
    void testGetTasks() throws Exception {
        taskService.addTask("Etape 2");
        taskService.addTask("Etape 3");
        taskService.getTasks();

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Etape 2"))
                .andExpect(jsonPath("$[1].description").value("Etape 3"));
    }

    @Test
    void testAddTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Etape 4\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testTaskTerminated() throws Exception {
        TaskEntity task = taskService.addTask("Etape 5");

        mockMvc.perform(put("/tasks/" + task.getId() + "/terminated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("Terminée"))
                .andExpect(jsonPath("$.description").value("Etape 5"));
    }

    @Test
    void testDeleteExpect204() throws Exception {
        TaskEntity task = taskService.addTask("Etape 6");

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteExpect404() throws Exception {
        TaskEntity task = taskService.addTask("Etape 6");
        taskService.deleteTask(task.getId());

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isNotFound());
    }
}
