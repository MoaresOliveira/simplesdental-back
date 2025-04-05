package com.simplesdental.product.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplesdental.product.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public class ProductV2DTO {

    private Long id;

    @NotBlank
    @Length(max = 100)
    private String name;

    @Length(max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal price;

    @NotNull
    private Boolean status;

    private Integer code;

    @NotNull
    @JsonIgnoreProperties({"products"})
    private Category category;

    public ProductV2DTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Length(max = 100) String getName() {
        return name;
    }

    public void setName(@NotBlank @Length(max = 100) String name) {
        this.name = name;
    }

    public @Length(max = 255) String getDescription() {
        return description;
    }

    public void setDescription(@Length(max = 255) String description) {
        this.description = description;
    }

    public @NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull @DecimalMin(value = "0", inclusive = false) BigDecimal price) {
        this.price = price;
    }

    public @NotNull Boolean getStatus() {
        return status;
    }

    public void setStatus(@NotNull Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public @NotNull Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull Category category) {
        this.category = category;
    }

}
