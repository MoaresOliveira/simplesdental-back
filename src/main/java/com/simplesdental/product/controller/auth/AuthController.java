package com.simplesdental.product.controller.auth;

import com.simplesdental.product.controller.auth.swagger.GetAuthContext;
import com.simplesdental.product.controller.auth.swagger.Login;
import com.simplesdental.product.controller.auth.swagger.Register;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.LoginRequestDTO;
import com.simplesdental.product.model.dto.request.UserRegisterDTO;
import com.simplesdental.product.model.dto.response.TokenResponseDTO;
import com.simplesdental.product.model.dto.response.UserDTO;
import com.simplesdental.product.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth Controller", description = "Operações de login, registro e contexto do usuário autenticado")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Login
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        log.info("Usuário autenticado com email {}", request.email());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Register
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO request) {
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }

    @GetMapping("/context")
    @GetAuthContext
    public UserDTO getAuthContext(@RequestAttribute("user") User user) {
        UserDTO userDTO = new UserDTO(user);
        log.debug("Auth Context {}", userDTO);
        return userDTO;
    }
}
