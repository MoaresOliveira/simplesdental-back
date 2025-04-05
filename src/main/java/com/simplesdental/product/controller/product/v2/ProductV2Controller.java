package com.simplesdental.product.controller.product.v2;

import com.simplesdental.product.controller.product.swagger.v2.*;
import com.simplesdental.product.mapper.ProductMapper;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.model.dto.ProductV2DTO;
import com.simplesdental.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Controller v2")
@RestController
@RequestMapping("/api/v2/products")
public class ProductV2Controller {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductV2Controller(ProductService productService) {
        this.productService = productService;
        this.productMapper = new ProductMapper();
    }

    @GetMapping
    @Transactional
    @GetAllProductsV2
    public ResponseEntity<Page<ProductV2DTO>> getAllProducts(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Product> products = productService.findAllWithNumericCode(pageable);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        products.forEach(product -> {
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
        });
        return ResponseEntity.ok(productMapper.toV2DTOPage(products));
    }

    @GetMapping("/{id}")
    @GetProductByIdV2
    public ResponseEntity<ProductV2DTO> getProductById(@PathVariable Long id) {
        return productService.findByIdWithNumericCode(id)
                .map(product -> {
                    if (product.getCategory() != null) {
                        Hibernate.initialize(product.getCategory());
                    }
                    return ResponseEntity.ok(productMapper.toV2DTO(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CreateProductV2
    public ProductV2DTO createProduct(@Valid @RequestBody ProductV2DTO productV2DTO) {
        Product product = productMapper.toEntity(productV2DTO);
        return productMapper.toV2DTO(productService.save(product));
    }

    @PutMapping("/{id}")
    @UpdateProductV2
    public ResponseEntity<ProductV2DTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductV2DTO productV2DTO) {
        Product product = productMapper.toEntity(productV2DTO);
        return productService.findByIdWithNumericCode(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return ResponseEntity.ok(productMapper.toV2DTO(productService.save(product)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @DeleteProductV2
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.findByIdWithNumericCode(id)
                .map(product -> {
                    productService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}