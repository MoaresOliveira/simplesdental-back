package com.simplesdental.product.controller.product.swagger.v1;

import com.simplesdental.product.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
                                schema = @Schema(implementation = Product.class),
                                examples = @ExampleObject(
                                        value = """
                                                [
                                                     {
                                                         "id": 1,
                                                         "name": "Smartphone XYZ",
                                                         "description": "Smartphone com 8GB RAM e 128GB de armazenamento",
                                                         "price": 1299.99,
                                                         "status": true,
                                                         "code": "PROD-001",
                                                         "category": {
                                                             "id": 3,
                                                             "name": "Smartphones",
                                                             "description": "Telefones celulares e acessórios"
                                                         }
                                                     }
                                                ]
                                                """)
                        )
                ),
                @ApiResponse(description = "No Content | Não há produtos", responseCode = "204", content = @Content())})
public @interface GetAllProductsV1 {
}
