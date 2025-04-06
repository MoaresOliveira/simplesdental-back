package com.simplesdental.product.model.dto.response;

import com.simplesdental.product.model.User;

import java.io.Serializable;

public record UserDTO(Long id, String name, String email, String role) implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
    }
}
