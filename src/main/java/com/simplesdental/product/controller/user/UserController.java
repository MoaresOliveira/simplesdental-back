package com.simplesdental.product.controller.user;

import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.PasswordRequestDTO;
import com.simplesdental.product.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "operações sobre dados de usuário")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordRequestDTO request,
                                            @RequestAttribute("user") User user) {
        user.setPassword(request.password());
        userService.save(user);
        return ResponseEntity.ok().build();
    }

}
