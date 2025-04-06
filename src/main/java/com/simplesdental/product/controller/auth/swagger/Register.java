package com.simplesdental.product.controller.auth.swagger;

import com.simplesdental.product.model.dto.request.UserRegisterDTO;
import com.simplesdental.product.model.dto.response.ErrorResponseDTO;
import com.simplesdental.product.model.dto.response.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Registrar usuário",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = UserRegisterDTO.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "name": "User example",
                                          "email": "example@email.com",
                                          "password": "usuario123"
                                        }
                                        """)
                )
        ),
        responses = {
                @ApiResponse(
                        description = "Usuário criado",
                        responseCode = "201",
                        content = @Content(
                                schema = @Schema(implementation = UserDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "id": 1,
                                                    "name": "User example",
                                                    "email": "example@email.com",
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
                                                        'password' não deve ser nulo ou em branco;
                                                        'name' não deve ser nulo ou em branco;
                                                        'email' não deve ser nulo ou em branco;
                                                        """,
                                                value = """
                                                    {
                                                        "timestamp": 1743957730798,
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/v1/auth/register",
                                                        "fields": {
                                                            "password": "must not be blank",
                                                            "name": "must not be blank",
                                                            "email": "must not be blank"
                                                        }
                                                    }
                                                """),
                                        @ExampleObject(
                                                name = "Exemplo 2",
                                                description = "'email' deve ser um endereço de e-mail bem formado",
                                                value = """
                                                    {
                                                        "timestamp": 1743955952720,
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/v1/auth/register",
                                                        "fields": {
                                                            "email": "must be a well-formed email address"
                                                        }
                                                    }
                                                """)
                                }
                        ))
        }
)
public @interface Register {
}
