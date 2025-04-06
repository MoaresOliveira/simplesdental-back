package com.simplesdental.product.controller.category;

import com.simplesdental.product.controller.category.swagger.*;
import com.simplesdental.product.model.Category;
import com.simplesdental.product.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category Controller", description = "Operações CRUD para categorias")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @GetAllCategories
    public ResponseEntity<Page<Category>> getAllCategories(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @GetCategoryById
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @UpdateCategory
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        return categoryService.findById(id)
                .map(existingCategory -> {
                    category.setId(id);
                    return ResponseEntity.ok(categoryService.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CreateCategory
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping("/{id}")
    @DeleteCategory
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(category -> {
                    categoryService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}