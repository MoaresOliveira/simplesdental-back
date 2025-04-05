package com.simplesdental.product.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordRequestDTO(@NotBlank String password) {
}
