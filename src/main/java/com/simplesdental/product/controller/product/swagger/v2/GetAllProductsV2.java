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
@Operation(summary = "Buscar todos produtos",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = ProductV2Page.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "content": [
                                                        {
                                                            "id": 1,
                                                            "name": "Smartphone XYZ",
                                                            "description": "Smartphone com 8GB RAM e 128GB de armazenamento",
                                                            "price": 1299.99,
                                                            "status": true,
                                                            "code": 123,
                                                            "category": {
                                                                "id": 3,
                                                                "name": "Smartphones",
                                                                "description": "Telefones celulares e acessórios"
                                                            }
                                                        }
                                                    ],
                                                    "pageable": {
                                                        "pageNumber": 0,
                                                        "pageSize": 10,
                                                        "sort": {
                                                            "empty": true,
                                                            "sorted": false,
                                                            "unsorted": true
                                                        },
                                                        "offset": 0,
                                                        "unpaged": false,
                                                        "paged": true
                                                    },
                                                    "last": false,
                                                    "totalElements": 1,
                                                    "totalPages": 1,
                                                    "size": 10,
                                                    "number": 0,
                                                    "sort": {
                                                        "empty": true,
                                                        "sorted": false,
                                                        "unsorted": true
                                                    },
                                                    "first": true,
                                                    "numberOfElements": 1,
                                                    "empty": false
                                                }
                                                """)
                        )
                ),
                @ApiResponse(description = "No Content | Não há produtos", responseCode = "204", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface GetAllProductsV2 {
}
