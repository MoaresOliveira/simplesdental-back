package com.simplesdental.product.controller.category.swagger;

import com.simplesdental.product.model.Category;
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
@Operation(summary = "Atualiza categoria",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = Category.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "name": "Eletrônicos",
                                          "description": "Produtos relacionados à tecnologia e eletrônicos"
                                        }
                                        """)
                )
        ),
        responses = {
                @ApiResponse(
                        description = "Categoria atualizada",
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = Category.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                  "id": 1,
                                                  "name": "Eletrônicos",
                                                  "description": "Produtos relacionados à tecnologia e eletrônicos",
                                                  "products": null
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
                                                        'description' não deve maior que 255 caracteres;
                                                        """,
                                                value = """
                                                    {
                                                        "timestamp": "2025-04-04T18:30:00Z",
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/categories/1",
                                                        "fields": {
                                                          "name": "must not be blank",
                                                          "description": "length must be between 0 and 255",
                                                        }
                                                    }
                                                """),
                                        @ExampleObject(
                                                name = "Exemplo 2",
                                                description = "'name' não deve ser maior que 100 caracteres;",
                                                value = """
                                                    {
                                                        "timestamp": "2025-04-04T18:30:00Z",
                                                        "status": 400,
                                                        "error": "Bad Request",
                                                        "path": "/api/categories/1",
                                                        "fields": {
                                                          "name": "length must be between 0 and 100",
                                                        }
                                                    }
                                                """)
                                }
                        )),
                @ApiResponse(description = "Not Found | Categoria não encontrada", responseCode = "404", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface UpdateCategory {
}
