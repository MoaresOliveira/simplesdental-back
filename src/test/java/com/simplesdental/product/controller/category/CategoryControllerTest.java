package com.simplesdental.product.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.product.config.TestSecurityConfig;
import com.simplesdental.product.config.UserAuthenticationFilter;
import com.simplesdental.product.model.Category;
import com.simplesdental.product.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CategoryController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UserAuthenticationFilter.class)
})
@Import(TestSecurityConfig.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Eletrônicos");
        category.setDescription("Categoria de produtos eletrônicos");
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(category), pageable, 1);

        when(categoryService.findAll(any(Pageable.class))).thenReturn(categoryPage);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Eletrônicos"));
    }

    @Test
    void shouldReturnNoContentWhenNoCategoriesFound() throws Exception {
        when(categoryService.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        when(categoryService.findById(1L))
                .thenReturn(Optional.of(category));

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Eletrônicos"));
    }

    @Test
    void shouldReturnNotFoundWhenCategoryDoesNotExist() throws Exception {
        when(categoryService.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCategory() throws Exception {
        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Eletrônicos"));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        when(categoryService.findById(1L)).thenReturn(Optional.of(category));
        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Eletrônicos"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentCategory() throws Exception {
        when(categoryService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        when(categoryService.findById(1L)).thenReturn(Optional.of(category));

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentCategory() throws Exception {
        when(categoryService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNotFound());
    }
}