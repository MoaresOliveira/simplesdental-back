package com.simplesdental.product.controller.product.v1;

import com.simplesdental.product.controller.product.swagger.v1.*;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Controller v1")
@RestController
@RequestMapping("/api/products")
public class ProductV1Controller {

    private final ProductService productService;

    @Autowired
    public ProductV1Controller(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Transactional
    @GetAllProductsV1
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        products.forEach(product -> {
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
        });
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @GetProductByIdV1
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findByIdWithAlphanumericCode(id)
                .map(product -> {
                    if (product.getCategory() != null) {
                        Hibernate.initialize(product.getCategory());
                    }
                    return ResponseEntity.ok(product);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CreateProductV1
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    @UpdateProductV1
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        return productService.findByIdWithAlphanumericCode(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return ResponseEntity.ok(productService.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @DeleteProductV1
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.findByIdWithAlphanumericCode(id)
                .map(product -> {
                    productService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}