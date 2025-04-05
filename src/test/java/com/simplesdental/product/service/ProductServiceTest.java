package com.simplesdental.product.service;

import com.simplesdental.product.model.Product;
import com.simplesdental.product.model.dto.ProductV2DTO;
import com.simplesdental.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("19.99"));
        product.setStatus(true);
        product.setCode("TP001");
    }

    @Test
    void shouldSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(1L);
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldGetAllProductsWithAlphanumericCode() {
        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);
        when(productRepository.findAllWithAlphanumericCode(any(Pageable.class))).thenReturn(productPage);

        Page<Product> products = productService.findAll(pageable);

        assertThat(products).isNotNull();
        assertThat(products.getTotalElements()).isEqualTo(1);
        verify(productRepository, times(1)).findAllWithAlphanumericCode(any(Pageable.class));
    }

    @Test
    void shouldGetAllProductsWithNumericCode() {
        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);
        when(productRepository.findAllWithNumericCode(any(Pageable.class))).thenReturn(productPage);

        Page<Product> products = productService.findAllWithNumericCode(pageable);

        assertThat(products).isNotNull();
        assertThat(products.getTotalElements()).isEqualTo(1);
        verify(productRepository, times(1)).findAllWithNumericCode(any(Pageable.class));
    }

    @Test
    void shouldGetProductByIdWithAlphanumericCode() {
        when(productRepository.findByIdWithAlphanumericCode(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findByIdWithAlphanumericCode(1L);

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getId()).isEqualTo(1L);
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findByIdWithAlphanumericCode(1L);
    }

    @Test
    void shouldGetProductByIdWithNumericCode() {
        when(productRepository.findByIdWithNumericCode(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findByIdWithNumericCode(1L);

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getId()).isEqualTo(1L);
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findByIdWithNumericCode(1L);
    }

    @Test
    void shouldDeleteProductById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}