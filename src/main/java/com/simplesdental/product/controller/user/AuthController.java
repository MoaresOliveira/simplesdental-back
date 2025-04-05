package com.simplesdental.product.controller.user;

import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.LoginRequestDTO;
import com.simplesdental.product.model.dto.request.UserRegisterDTO;
import com.simplesdental.product.model.dto.response.AuthContextDTO;
import com.simplesdental.product.model.dto.response.TokenResponseDTO;
import com.simplesdental.product.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.Cacheable;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "operações de login, registro e contexto do usuário autenticado")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthContextDTO> register(@Valid @RequestBody UserRegisterDTO request) {
        User user = authService.register(request);
        return ResponseEntity.ok(new AuthContextDTO(user));
    }

    @GetMapping("/context")
    public AuthContextDTO getAuthContext(@RequestAttribute("user") User user) {
        return new AuthContextDTO(user);
    }
}
