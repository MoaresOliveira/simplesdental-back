package com.simplesdental.product.controller.product.swagger.v2;

import com.simplesdental.product.model.dto.ProductV2DTO;
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
@Operation(summary = "Buscar produto por ID",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = ProductV2DTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                  "id": 1,
                                                  "name": "Aspirador",
                                                  "description": "",
                                                  "price": 100.00,
                                                  "status": true,
                                                  "code": 123,
                                                  "category": {
                                                    "id": 1,
                                                    "name": "Eletrônicos",
                                                    "description": "Produtos relacionados à tecnologia e eletrônicos"
                                                  }
                                                }
                                                """)
                        )
                ),
                @ApiResponse(description = "Not Found | Produto não encontrado", responseCode = "404", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface GetProductByIdV2 {
}
