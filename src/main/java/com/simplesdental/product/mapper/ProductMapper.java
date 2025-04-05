package com.simplesdental.product.mapper;

import com.simplesdental.product.model.Product;
import com.simplesdental.product.model.dto.ProductV2DTO;
import org.springframework.data.domain.Page;

public class ProductMapper {

    public ProductV2DTO toV2DTO(Product product) {
        ProductV2DTO dto = new ProductV2DTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStatus(product.getStatus());
        dto.setCategory(product.getCategory());
        dto.setCode(Integer.parseInt(product.getCode()));
        return dto;
    }

    public Product toEntity(ProductV2DTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStatus(dto.getStatus());
        product.setCategory(dto.getCategory());
        product.setCode(dto.getCode().toString());
        return product;
    }

    public Page<ProductV2DTO> toV2DTOPage(Page<Product> products) {
        ProductMapper productMapper = new ProductMapper();
        return products.map(productMapper::toV2DTO);
    }
}
