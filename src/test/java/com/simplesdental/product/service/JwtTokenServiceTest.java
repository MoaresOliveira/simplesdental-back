package com.simplesdental.product.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.simplesdental.product.enums.UserRole;
import com.simplesdental.product.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {


    private JwtTokenService jwtTokenService;

    @BeforeEach
    void setUp() {
        jwtTokenService = new JwtTokenService("secret");
    }

    @Test
    void shouldGenerateAndParseTokenCorrectly() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(UserRole.USER);

        String token = jwtTokenService.generateToken(user);
        String subject = jwtTokenService.getSubjectFromToken(token);

        assertThat(subject).isEqualTo(user.getEmail());
    }

    @Test
    void shouldExtractSubjectFromValidToken() {
        String email = "example@simplesdental.com";
        User user = new User();
        user.setId(42L);
        user.setEmail(email);
        user.setRole(UserRole.ADMIN);

        String token = jwtTokenService.generateToken(user);

        String extractedSubject = jwtTokenService.getSubjectFromToken(token);

        assertThat(extractedSubject).isEqualTo(email);
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalid.token.here";

        assertThrows(
                JWTVerificationException.class,
                () -> jwtTokenService.getSubjectFromToken(invalidToken)
        );
    }

}