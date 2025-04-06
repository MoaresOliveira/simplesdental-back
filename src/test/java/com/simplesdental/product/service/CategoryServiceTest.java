package com.simplesdental.product.service;

import com.simplesdental.product.model.Category;
import com.simplesdental.product.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;


    @Test
    void findAllShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> mockPage = new PageImpl<>(List.of(new Category()));

        when(categoryRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Category> result = categoryService.findAll(pageable);

        assertThat(result).isEqualTo(mockPage);
        verify(categoryRepository).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnOptional() {
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.findById(1L);

        assertThat(result).isPresent().contains(category);
        verify(categoryRepository).findById(1L);
    }

    @Test
    void saveShouldReturnSavedCategory() {
        Category category = new Category();
        category.setName("Categoria Teste");

        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.save(category);

        assertThat(result).isEqualTo(category);
        verify(categoryRepository).save(category);
    }

    @Test
    void deleteByIdShouldCallRepository() {
        Long id = 1L;

        categoryService.deleteById(id);

        verify(categoryRepository).deleteById(id);
    }
}