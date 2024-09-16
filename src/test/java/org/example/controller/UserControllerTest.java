package org.example.controller;

import org.example.controller.UserController;
import org.example.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.example.service.UserService;
import org.example.Application;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindAllUsers() throws Exception {
        when(userService.getUser()).thenReturn(List.of(new User(), new User()));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void shouldFindUserById() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Maks", "Maks@gmail.com");
        when(userService.getUserById(userId)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Maks"))
                .andExpect(jsonPath("$.email").value("Maks@gmail.com"));
    }
    @Test
    void shouldCreate() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Maks", "maks@gmail.com");
        when(userService.create(new User(null, "Maks", "maks@gmail.com"))).thenReturn(user);

        mockMvc.perform(get("/api/v1/create")
                        .param("name", "Maks")
                        .param("email", "maks@gmail.com"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Maks"))
                .andExpect(jsonPath("$.email").value("maks@gmail.com"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User updatedUser = new User(userId, "New Name", "new.email@example.com");

        when(userService.update(userId, updatedUser)).thenReturn(updatedUser);

        String updatedUserJson = "{\"id\":\"" + userId.toString() + "\",\"name\":\"New Name\",\"email\":\"new.email@example.com\"}";

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.email").value("new.email@example.com"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

}