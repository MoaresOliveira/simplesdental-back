package com.simplesdental.product.controller.user.swagger;

import com.simplesdental.product.model.dto.request.UserUpdateDTO;
import com.simplesdental.product.model.dto.response.ErrorResponseDTO;
import com.simplesdental.product.model.dto.response.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Atualiza usuário",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = UserUpdateDTO.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                            "name": "Usuário Exemplo",
                                            "email": "exemplo@email.com",
                                            "role": "USER"
                                        }
                                        """)
                )
        ),
        responses = {
                @ApiResponse(
                        description = "Usuário atualizado",
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
                @ApiResponse(description = "Bad Request", responseCode = "400",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = {
                                        @ExampleObject(
                                                name = "Exemplo 1",
                                                description = """
                                                        'name' não deve ser nulo ou em branco;
                                                        'email' não deve ser nulo ou em branco;
                                                        'role' não deve ser nulo;
                                                        """,
                                                value = """
                                                    {
                                                        "timestamp": "2025-04-04T18:30:00Z",
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/v1/users/1",
                                                        "fields": {
                                                          "name": "must not be blank",
                                                          "email": "must not be blank",
                                                          "role": "must not be null",
                                                        }
                                                    }
                                                """),
                                        @ExampleObject(
                                                name = "Exemplo 2",
                                                description = "'email' deve ser um endereço de e-mail bem formado",
                                                value = """
                                                    {
                                                        "timestamp": "2025-04-04T18:30:00Z",
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/v1/users/1",
                                                        "fields": {
                                                          "email": "must be a well-formed email address"
                                                        }
                                                    }
                                                """)
                                }
                        )),
                @ApiResponse(description = "Not Found | Usuário não encontrado", responseCode = "404", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface UpdateUser {
}
