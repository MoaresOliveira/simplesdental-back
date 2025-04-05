package com.simplesdental.product.model.dto.response;

import com.simplesdental.product.enums.UserRole;
import com.simplesdental.product.model.User;

public record AuthContextDTO(Long id, String email, UserRole role) {

    public AuthContextDTO(User user) {
        this(user.getId(), user.getEmail(), user.getRole());
    }
}
