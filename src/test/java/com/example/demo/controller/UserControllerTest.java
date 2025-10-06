package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@Disabled
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/users/getAllUsers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById_notFound() throws Exception {
        when(userService.getUser(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/getUserById/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        User in = User.builder().name("Jane").email("jane@example.com").build();
        User out = User.builder().id(1L).name("Jane").email("jane@example.com").build();
        when(userService.createUser(any(User.class))).thenReturn(out);

        String json = "{\"name\":\"Jane\",\"email\":\"jane@example.com\",\"address\":\"x\"}";

        mockMvc.perform(post("/api/v1/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/getUserById/1"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        User existing = User.builder().id(1L).name("Old").email("old@example.com").build();
        User updated = User.builder().id(1L).name("New").email("new@example.com").build();
        when(userService.getUser(1L)).thenReturn(Optional.of(existing));
        when(userService.createUser(any(User.class))).thenReturn(updated);

        String json = "{\"name\":\"New\",\"email\":\"new@example.com\",\"address\":\"addr\"}";

        mockMvc.perform(put("/api/v1/users/updateUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        User existing = User.builder().id(1L).name("ToDelete").email("del@example.com").build();
        when(userService.getUser(1L)).thenReturn(Optional.of(existing));
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/v1/users/deleteUser/1"))
                .andExpect(status().isNoContent());
    }
}
