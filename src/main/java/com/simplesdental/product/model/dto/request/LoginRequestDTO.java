package com.simplesdental.product.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@Email @NotBlank String email, @NotBlank String password) {
}
