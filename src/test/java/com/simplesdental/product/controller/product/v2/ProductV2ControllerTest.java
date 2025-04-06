package com.simplesdental.product.controller.product.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.product.config.TestSecurityConfig;
import com.simplesdental.product.config.UserAuthenticationFilter;
import com.simplesdental.product.model.Category;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.model.dto.ProductV2DTO;
import com.simplesdental.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductV2Controller.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UserAuthenticationFilter.class)
})
@Import(TestSecurityConfig.class)
public class ProductV2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    private ProductV2DTO productV2DTO;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("19.99"));
        product.setStatus(true);
        product.setCode("123");
        product.setCategory(category);

        productV2DTO = new ProductV2DTO();
        productV2DTO.setId(1L);
        productV2DTO.setName("Test Product");
        productV2DTO.setDescription("Test Description");
        productV2DTO.setPrice(new BigDecimal("19.99"));
        productV2DTO.setStatus(true);
        productV2DTO.setCode(123);
        productV2DTO.setCategory(category);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/v2/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productV2DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);
        when(productService.findAllWithNumericCode(any(Pageable.class))).thenReturn(productPage);

        mockMvc.perform(get("/api/v2/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(product.getId()))
                .andExpect(jsonPath("$.content[0].name").value(product.getName()))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void shouldGetProductById() throws Exception {
        when(productService.findByIdWithNumericCode(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/v2/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void shouldReturn404WhenGetProductByIdNotFound() throws Exception {
        when(productService.findByIdWithNumericCode(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v2/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        when(productService.findByIdWithNumericCode(1L)).thenReturn(Optional.of(product));
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/v2/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productV2DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void shouldReturn404WhenUpdateProductNotFound() throws Exception {
        when(productService.findByIdWithNumericCode(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v2/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productV2DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        when(productService.findByIdWithNumericCode(1L)).thenReturn(Optional.of(product));
        doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/v2/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeleteProductNotFound() throws Exception {
        when(productService.findByIdWithNumericCode(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v2/products/1"))
                .andExpect(status().isNotFound());
    }
}