package com.simplesdental.product.model.dto.request;

import com.simplesdental.product.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserUpdateDTO {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UserRole role;

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @NotNull UserRole getRole() {
        return role;
    }

    public void setRole(@NotNull UserRole role) {
        this.role = role;
    }
}
