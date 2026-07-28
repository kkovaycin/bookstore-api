package com.pinsoftstaj.bookstore_api.service;

import com.pinsoftstaj.bookstore_api.dto.category.CategoryRequest;
import com.pinsoftstaj.bookstore_api.dto.category.CategoryResponse;
import com.pinsoftstaj.bookstore_api.entity.Category;
import com.pinsoftstaj.bookstore_api.exception.DuplicateResourceException;
import com.pinsoftstaj.bookstore_api.exception.ResourceNotFoundException;
import com.pinsoftstaj.bookstore_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public CategoryResponse findById(Long id) {
        return CategoryResponse.from(
                getEntity(id)
        );
    }

    @Transactional
    public CategoryResponse create(
            CategoryRequest request
    ) {
        String name = request.name().trim();

        if (categoryRepository
                .existsByNameIgnoreCase(name)) {

            throw new DuplicateResourceException(
                    "Bu kategori zaten kayıtlı: " + name
            );
        }

        Category category =
                new Category(name);

        return CategoryResponse.from(
                categoryRepository.save(category)
        );
    }

    @Transactional
    public CategoryResponse update(
            Long id,
            CategoryRequest request
    ) {
        Category category = getEntity(id);

        String name = request.name().trim();

        if (categoryRepository
                .existsByNameIgnoreCaseAndIdNot(
                        name,
                        id
                )) {

            throw new DuplicateResourceException(
                    "Bu kategori adı başka bir kayıtta kullanılıyor: "
                            + name
            );
        }

        category.update(name);

        return CategoryResponse.from(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = getEntity(id);

        categoryRepository.delete(category);
    }

    public Category getEntity(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Kategori bulunamadı. id=" + id
                        )
                );
    }
}
