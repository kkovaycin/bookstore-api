package com.pinsoftstaj.bookstore_api.controller;

import com.pinsoftstaj.bookstore_api.dto.category.CategoryRequest;
import com.pinsoftstaj.bookstore_api.dto.category.CategoryResponse;
import com.pinsoftstaj.bookstore_api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService
    ) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>>
    getAllCategories() {

        return ResponseEntity.ok(
                categoryService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse>
    getCategoryById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                categoryService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<CategoryResponse>
    createCategory(
            @Valid
            @RequestBody
            CategoryRequest request
    ) {
        CategoryResponse createdCategory =
                categoryService.create(request);

        URI location = URI.create(
                "/api/categories/"
                        + createdCategory.id()
        );

        return ResponseEntity
                .created(location)
                .body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse>
    updateCategory(
            @PathVariable Long id,

            @Valid
            @RequestBody
            CategoryRequest request
    ) {
        return ResponseEntity.ok(
                categoryService.update(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
