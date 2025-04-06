package com.simplesdental.product.controller.auth.swagger;

import com.simplesdental.product.model.dto.request.LoginRequestDTO;
import com.simplesdental.product.model.dto.response.ErrorResponseDTO;
import com.simplesdental.product.model.dto.response.TokenResponseDTO;
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
        summary = "Autenticar usuário",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = LoginRequestDTO.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "email": "example@email.com",
                                          "password": "strongPassword"
                                        }
                                        """)
                )
        ),
        responses = {
                @ApiResponse(
                        description = "Autenticação bem sucedida",
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = TokenResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "token": "fakeyJhbGciOiJIUzI1..."
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
                                                        'email' não deve ser nulo ou em branco;
                                                        """,
                                                value = """
                                                    {
                                                        "timestamp": 1743957039614,
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/v1/auth/login",
                                                        "fields": {
                                                            "password": "must not be blank",
                                                            "email": "must not be blank"
                                                        }
                                                    }
                                                """),
                                        @ExampleObject(
                                                name = "Exemplo 2",
                                                description = "'email' deve ser um endereço de e-mail bem formado",
                                                value = """
                                                    {
                                                        "timestamp": 1743957039614,
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/v1/auth/login",
                                                        "fields": {
                                                            "email": "must be a well-formed email address"
                                                        }
                                                    }
                                                """)
                                }
                        ))
        }
)
public @interface Login {
}
