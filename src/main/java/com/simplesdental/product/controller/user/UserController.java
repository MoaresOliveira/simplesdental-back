package com.simplesdental.product.controller.user;

import com.simplesdental.product.controller.user.swagger.*;
import com.simplesdental.product.mapper.UserMapper;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.PasswordRequestDTO;
import com.simplesdental.product.model.dto.request.UserUpdateDTO;
import com.simplesdental.product.model.dto.response.UserDTO;
import com.simplesdental.product.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "Operações sobre dados de usuário")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        userMapper = new UserMapper();
    }

    @PutMapping("/password")
    @CacheEvict(value = "userByEmail", key = "#user.email")
    @UpdatePassword
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordRequestDTO request,
                                            @RequestAttribute("user") User user) {
        user.setPassword(request.password());
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @GetAllUsers
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userMapper.toDTOPage(users));
    }

    @GetMapping("/{id}")
    @GetUserById
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @UpdateUser
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @DeleteUser
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    userService.deleteUser(user);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
