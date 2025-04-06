package com.simplesdental.product.service;

import com.simplesdental.product.config.SecurityConfig;
import com.simplesdental.product.exception.ResourceNotFoundException;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.UserUpdateDTO;
import com.simplesdental.product.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityConfig securityConfig;
    @InjectMocks
    private UserService userService;

    @Test
    void saveShouldEncodePasswordAndSaveUser() {
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "ENCODED_" + invocation.getArgument(0));

        User user = new User();
        user.setPassword("123456");

        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        User saved = userService.save(user);

        assertThat(saved.getPassword()).isEqualTo("ENCODED_123456");
        verify(userRepository).save(user);
    }

    @Test
    void findByEmailShouldReturnUserIfExists() {
        User user = new User();
        user.setEmail("test@email.com");

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("test@email.com");

        assertThat(result).isEqualTo(user);
        verify(userRepository).findByEmail("test@email.com");
    }

    @Test
    void findByEmailShouldThrowExceptionIfNotFound() {
        when(userRepository.findByEmail("notfound@email.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findByEmail("notfound@email.com"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("user");
    }

    @Test
    void findAllShouldReturnPagedUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(List.of(new User()));

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<User> result = userService.findAll(pageable);

        assertThat(result).hasSize(1);
        verify(userRepository).findAll(pageable);
    }

    @Test
    void deleteUserShouldCallRepositoryDelete() {
        User user = new User();
        user.setEmail("test@email.com");

        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    void updateUserShouldMapAndSaveUpdatedUser() {
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "ENCODED_" + invocation.getArgument(0));

        Long id = 1L;
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setName("Updated Name");
        dto.setEmail("updated@email.com");

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setPassword("hashed_pass");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User updated = userService.updateUser(id, dto);

        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getEmail()).isEqualTo("updated@email.com");
        assertThat(updated.getName()).isEqualTo("Updated Name");
        assertThat(updated.getPassword()).isEqualTo("ENCODED_hashed_pass");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserShouldThrowIfNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(99L, new UserUpdateDTO()))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
