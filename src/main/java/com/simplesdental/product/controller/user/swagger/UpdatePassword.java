package com.simplesdental.product.controller.user.swagger;

import com.simplesdental.product.model.dto.request.PasswordRequestDTO;
import com.simplesdental.product.model.dto.response.ErrorResponseDTO;
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
@Operation(summary = "Atualiza senha do usuário",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = PasswordRequestDTO.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "password": "strongPassword"
                                        }
                                        """)
                )
        ),
        responses = {
                @ApiResponse(
                        description = "Senha atualizada",
                        responseCode = "200",
                        content = @Content()
                ),
                @ApiResponse(description = "Bad Request", responseCode = "400",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples =
                                @ExampleObject(
                                        name = "Exemplo 1",
                                        description = """
                                                'password' não deve ser nulo ou em branco;
                                                """,
                                        value = """
                                                    {
                                                        "timestamp": 1743956078422,
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/v1/users/password",
                                                        "fields": {
                                                            "password": "must not be blank"
                                                        }
                                                    }
                                                """)
                        )),
                @ApiResponse(description = "Not Found | Usuário não encontrado", responseCode = "404", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface UpdatePassword {
}
