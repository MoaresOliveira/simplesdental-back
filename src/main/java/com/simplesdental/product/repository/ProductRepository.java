package com.simplesdental.product.repository;

import com.simplesdental.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE code ~ '^[0-9]+$'", nativeQuery = true)
    Page<Product> findAllWithNumericCode(Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE code !~ '^[0-9]+$'", nativeQuery = true)
    Page<Product> findAllWithAlphanumericCode(Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE id = ? AND code ~ '^[0-9]+$'", nativeQuery = true)
    Optional<Product> findByIdWithNumericCode(Long id);

    @Query(value = "SELECT * FROM products WHERE id = ? AND code !~ '^[0-9]+$'", nativeQuery = true)
    Optional<Product> findByIdWithAlphanumericCode(Long id);
}