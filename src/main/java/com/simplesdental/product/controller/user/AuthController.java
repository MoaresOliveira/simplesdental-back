package com.simplesdental.product.controller.user;

import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.LoginRequestDTO;
import com.simplesdental.product.model.dto.request.UserRegisterDTO;
import com.simplesdental.product.model.dto.response.AuthContextDTO;
import com.simplesdental.product.model.dto.response.TokenResponseDTO;
import com.simplesdental.product.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "operações de login, registro e contexto do usuário autenticado")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        log.info("Usuário autenticado com email {}", request.email());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthContextDTO> register(@Valid @RequestBody UserRegisterDTO request) {
        User user = authService.register(request);
        return ResponseEntity.ok(new AuthContextDTO(user));
    }

    @GetMapping("/context")
    public AuthContextDTO getAuthContext(@RequestAttribute("user") User user) {
        AuthContextDTO authContextDTO = new AuthContextDTO(user);
        log.debug("Auth Context {}", authContextDTO);
        return authContextDTO;
    }
}
