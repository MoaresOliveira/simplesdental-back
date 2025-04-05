package com.simplesdental.product.controller.category.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Excluir categoria",
        responses = {
                @ApiResponse(description = "No Content | Categoria deletada", responseCode = "204", content = @Content()),
                @ApiResponse(description = "Not Found | Categoria não encontrada", responseCode = "404", content = @Content())})
public @interface DeleteCategory {
}
