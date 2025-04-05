package com.simplesdental.product.controller.category.swagger;

import com.simplesdental.product.model.Category;
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
@Operation(summary = "Buscar categoria por ID",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = Category.class),
                                examples = @ExampleObject(
                                        value = """
                                                    {
                                                      "id": 1,
                                                      "name": "Eletrônicos",
                                                      "description": "Produtos relacionados à tecnologia e eletrônicos",
                                                      "products": [
                                                        {
                                                          "id": 100,
                                                          "name": "Smartphone",
                                                          "description": "Celular com sistema Android",
                                                          "price": 1999.99
                                                        },
                                                        {
                                                          "id": 101,
                                                          "name": "Notebook",
                                                          "description": "Notebook com 16GB RAM",
                                                          "price": 4999.90
                                                        }
                                                      ]
                                                    }
                                                """)
                        )
                ),
                @ApiResponse(description = "Not Found | Categoria não encontrada", responseCode = "404", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface GetCategoryById {
}
