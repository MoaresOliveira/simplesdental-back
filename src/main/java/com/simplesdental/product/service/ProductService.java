package com.simplesdental.product.service;

import com.simplesdental.product.model.Product;
import com.simplesdental.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAllWithAlphanumericCode(pageable);
    }

    public Optional<Product> findByIdWithAlphanumericCode(Long id) {
        return productRepository.findByIdWithAlphanumericCode(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findAllWithNumericCode(Pageable pageable) {
        return productRepository.findAllWithNumericCode(pageable);
    }

    public Optional<Product> findByIdWithNumericCode(Long id) {
        return productRepository.findByIdWithNumericCode(id);
    }
}