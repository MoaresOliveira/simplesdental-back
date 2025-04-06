package com.simplesdental.product.controller.user.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Excluir usuário",
        responses = {
                @ApiResponse(description = "No Content | Usuário deletado", responseCode = "204", content = @Content()),
                @ApiResponse(description = "Not Found | Usuário não encontrado", responseCode = "404", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface DeleteUser {
}
