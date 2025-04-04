package com.simplesdental.product.controller.swagger.annotations.product;

import com.simplesdental.product.model.Product;
import com.simplesdental.product.model.dto.ErrorResponseDTO;
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
@Operation(summary = "Atualiza produto",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = Product.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "name": "Aspirador",
                                          "description": "",
                                          "price": 100.00,
                                          "status": true,
                                          "code": "PROD-012",
                                          "category": {
                                            "id": 1
                                          }
                                        }
                                        """)
                )
        ),
        responses = {
                @ApiResponse(
                        description = "Produto atualizado",
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = Product.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                  "id": 1,
                                                  "name": "Aspirador",
                                                  "description": "",
                                                  "price": 100.00,
                                                  "status": true,
                                                  "code": "PROD-012",
                                                  "category": {
                                                    "id": 1,
                                                    "name": null,
                                                    "description": null
                                                  }
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
                                                        'price' deve ser maior que 0;
                                                        'status' não pode ser nulo;
                                                        """,
                                                value = """
                                                            {
                                                                "timestamp": 1743807220504,
                                                                "status": 400,
                                                                "error": "Bad Request",
                                                                "path": "/api/products/1",
                                                                "fields": {
                                                                    "price": "must be greater than 0",
                                                                    "name": "must not be blank",
                                                                    "status": "must not be null"
                                                                }
                                                            }
                                                        """),
                                        @ExampleObject(
                                                name = "Exemplo 2",
                                                description = """
                                                        'name' não deve ser maior que 100 caracteres;
                                                        'description' não deve ser maior que 255 caracteres;
                                                        """,
                                                value = """
                                                            {
                                                                "timestamp": 1743807220504,
                                                                "status": 400,
                                                                "error": "Bad Request",
                                                                "path": "/api/products/1",
                                                                "fields": {
                                                                    "name": "length must be between 0 and 100",
                                                                    "description": "length must be between 0 and 255"
                                                                }
                                                            }
                                                        """)
                                }
                        )),
                @ApiResponse(description = "Not Found | Produto não encontrado", responseCode = "404", content = @Content())})
public @interface UpdateProduct {
}
