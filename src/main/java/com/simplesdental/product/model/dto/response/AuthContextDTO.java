package com.simplesdental.product.model.dto.response;

import com.simplesdental.product.model.User;

import java.io.Serializable;

public record AuthContextDTO(Long id, String email, String role) implements Serializable {

    private static final long serialVersionUID = 1L;

    public AuthContextDTO(User user) {
        this(user.getId(), user.getEmail(), user.getRole().name());
    }
}
