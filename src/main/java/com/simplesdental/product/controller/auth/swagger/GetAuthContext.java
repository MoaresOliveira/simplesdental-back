package com.simplesdental.product.controller.auth.swagger;

import com.simplesdental.product.model.dto.response.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Buscar usuário por ID",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = UserDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "id": 1,
                                                    "name": "Usuário Exemplo",
                                                    "email": "exemplo@email.com",
                                                    "role": "USER"
                                                }
                                                """)
                        )
                ),
                @ApiResponse(description = "Not Found | Usuário não encontrado", responseCode = "404", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface GetAuthContext {
}
