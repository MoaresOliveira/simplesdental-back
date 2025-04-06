package com.simplesdental.product.service;

import com.simplesdental.product.config.SecurityConfig;
import com.simplesdental.product.enums.UserRole;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.LoginRequestDTO;
import com.simplesdental.product.model.dto.request.UserRegisterDTO;
import com.simplesdental.product.model.dto.response.TokenResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private AutoCloseable closeable;

    private User user;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("maria@example.com");
        user.setPassword("senhaSegura");
        user.setRole(UserRole.USER);

        when(securityConfig.getAuthenticationManager()).thenReturn(authenticationManager);
    }

    @Test
    void testLoginReturnsToken() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("maria@example.com", "senha123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtTokenService.generateToken(user)).thenReturn("fake-jwt-token");

        TokenResponseDTO response = authService.login(loginRequest);

        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo("fake-jwt-token");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenService).generateToken(user);
    }

    @Test
    void testRegisterCreatesUserWithUserRole() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setName("Test User");
        registerDTO.setEmail("test@email.com");
        registerDTO.setPassword("senha123");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        when(userService.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = authService.register(registerDTO);

        verify(userService).save(userCaptor.capture());
        User captured = userCaptor.getValue();

        assertThat(captured.getEmail()).isEqualTo("test@email.com");
        assertThat(captured.getRole()).isEqualTo(UserRole.USER);
        assertThat(savedUser).isEqualTo(captured);
    }

    @Test
    void testLoadUserByUsernameReturnsUser() {
        when(userService.findByEmail("maria@example.com")).thenReturn(user);

        User result = (User) authService.loadUserByUsername("maria@example.com");

        assertThat(result).isEqualTo(user);
        verify(userService).findByEmail("maria@example.com");
    }

    @Test
    void testLoadUserByUsernameThrowsWhenNotFound() {
        when(userService.findByEmail("inexistente@example.com"))
                .thenThrow(new UsernameNotFoundException("Usuário não encontrado"));

        assertThatThrownBy(() -> authService.loadUserByUsername("inexistente@example.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Usuário não encontrado");

        verify(userService).findByEmail("inexistente@example.com");
    }
}