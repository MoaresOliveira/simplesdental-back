package com.simplesdental.product.controller.category.swagger;

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
@Operation(summary = "Buscar todas categorias",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                                schema = @Schema(implementation = CategoryPage.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "content": [
                                                        {
                                                            "id": 1,
                                                            "name": "Eletrônicos",
                                                            "description": "Produtos eletrônicos e gadgets",
                                                            "products": null
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
                                                    "last": true,
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
                @ApiResponse(description = "No Content | Não há categorias", responseCode = "204", content = @Content())})
@SecurityRequirement(name = "authenticationJWT")
public @interface GetAllCategories {
}
