package com.example.task.controller;

import com.example.task.entities.TaskEntity;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerUnitTests {
    @Autowired
    private MockMvc mockMvc; //Object used to make HTTP request on our API

    @MockitoBean
    private TaskService taskService; //Mock object TaskService automatically injected in TaskController instance

    @Test
    void hello_should_return_message() throws Exception {
        mockMvc.perform(get("/tasks/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to the Task Manager API!"));
    }


    @Test
    void testGetOne() throws Exception {
        TaskEntity task1 = new TaskEntity("Etape 1");
        when(taskService.getOneTask(task1.getId())).thenReturn(task1);

        mockMvc.perform(get("/tasks/"+task1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Etape 1"))
                .andExpect(jsonPath("$.description").isString())
                //JSON response send the UUID as a String, this is why I have to add toString()
                .andExpect(jsonPath("$.id").value(task1.getId().toString()))
                .andExpect(jsonPath("$.state").value("En cours"));
    }

    @Test
    void testGetTasks() throws Exception {
        List<TaskEntity> list = new ArrayList<>();
        TaskEntity task1 = new TaskEntity("Etape 2");
        TaskEntity task2 = new TaskEntity("Etape 3");
        list.add(task1);
        list.add(task2);
        when(taskService.getTasks()).thenReturn(list);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Etape 2"))
                .andExpect(jsonPath("$[1].description").value("Etape 3"));
    }

    @Test
    void testAddTask() throws Exception {
        when(taskService.addTask("Etape 4")).thenReturn(new TaskEntity("Etape 4"));

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Etape 4\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testTaskTerminated() throws Exception {
        TaskEntity task = new TaskEntity("Etape 5");
        task.setState("Terminée");
        when(taskService.taskTerminated(task.getId())).thenReturn(task);

        mockMvc.perform(put("/tasks/" + task.getId() + "/terminated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("Terminée"));
    }

    @Test
    void testDeleteExpect204() throws Exception {
        UUID id = UUID.randomUUID();
        when(taskService.deleteTask(id)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteExpect404() throws Exception {
        UUID id = UUID.randomUUID();
        when(taskService.deleteTask(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNotFound());
    }

}
