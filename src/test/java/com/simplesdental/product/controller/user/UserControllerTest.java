package com.simplesdental.product.controller.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.product.config.TestSecurityConfig;
import com.simplesdental.product.config.UserAuthenticationFilter;
import com.simplesdental.product.enums.UserRole;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.PasswordRequestDTO;
import com.simplesdental.product.model.dto.request.UserUpdateDTO;
import com.simplesdental.product.model.dto.response.UserDTO;
import com.simplesdental.product.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UserAuthenticationFilter.class)
})
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPassword("123456");

        userDTO = new UserDTO(user);
    }

    @Test
    void shouldUpdatePassword() throws Exception {
        PasswordRequestDTO passwordRequest = new PasswordRequestDTO("novaSenha");

        mockMvc.perform(put("/api/v1/users/password")
                        .requestAttr("user", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().isOk());

       verify(userService).save(any(User.class));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user), pageable, 1);

        when(userService.findAll(any(Pageable.class))).thenReturn(userPage);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].email").value("testuser@example.com"));
    }

    @Test
    void shouldReturnNoContentWhenNoUsersFound() throws Exception {
        when(userService.findAll(any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnUserById() throws Exception {
       when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
       when(userService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setName("Test User");
        updateDTO.setEmail("testuser@example.com");
        updateDTO.setRole(UserRole.ADMIN);
       when(userService.updateUser(eq(1L), any(UserUpdateDTO.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
       when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());

       verify(userService).deleteUser(user);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
       when(userService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }
}