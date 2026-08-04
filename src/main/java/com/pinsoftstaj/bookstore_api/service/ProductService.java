package com.pinsoftstaj.bookstore_api.service;

import com.pinsoftstaj.bookstore_api.dto.product.ProductRequest;
import com.pinsoftstaj.bookstore_api.dto.product.ProductResponse;
import com.pinsoftstaj.bookstore_api.entity.Category;
import com.pinsoftstaj.bookstore_api.entity.Product;
import com.pinsoftstaj.bookstore_api.exception.ResourceNotFoundException;
import com.pinsoftstaj.bookstore_api.repository.CategoryRepository;
import com.pinsoftstaj.bookstore_api.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public List<ProductResponse> search(
            String name,
            List<Long> categoryIds
    ) {
        String normalizedName =
                name == null
                        ? null
                        : name.trim();

        boolean hasName =
                normalizedName != null
                        && !normalizedName.isBlank();

        boolean hasCategories =
                categoryIds != null
                        && !categoryIds.isEmpty();

        List<Product> products;

        if (hasName && hasCategories) {
            products =
                    productRepository.findByNameOrCategoryIds(
                            normalizedName,
                            categoryIds
                    );
        } else if (hasName) {
            products =
                    productRepository
                            .findByNameContainingIgnoreCase(
                                    normalizedName
                            );
        } else if (hasCategories) {
            products =
                    productRepository.findByCategoryIdIn(
                            categoryIds
                    );
        } else {
            products = productRepository.findAll();
        }

        return products
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(
                findProductById(id)
        );
    }

    @Transactional
    public ProductResponse create(
            ProductRequest request
    ) {
        Category category =
                findCategoryById(request.categoryId());

        Product product = new Product(
                request.name().trim(),
                request.price(),
                normalizeExplanation(request.explanation()),
                normalizeBase64Image(request.base64Image()),
                category
        );

        return ProductResponse.from(
                productRepository.save(product)
        );
    }

    @Transactional
    public ProductResponse update(
            Long id,
            ProductRequest request
    ) {
        Product product = findProductById(id);

        Category category =
                findCategoryById(request.categoryId());

        product.update(
                request.name().trim(),
                request.price(),
                normalizeExplanation(request.explanation()),
                normalizeBase64Image(request.base64Image()),
                category
        );

        return ProductResponse.from(
                productRepository.save(product)
        );
    }

    @Transactional
    public void delete(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found. ID: " + id
                        )
                );
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found. ID: " + id
                        )
                );
    }

    private String normalizeExplanation(
            String explanation
    ) {
        if (
                explanation == null
                        || explanation.isBlank()
        ) {
            return null;
        }

        return explanation.trim();
    }

    private String normalizeBase64Image(
            String base64Image
    ) {
        if (
                base64Image == null
                        || base64Image.isBlank()
        ) {
            return null;
        }

        return base64Image.trim();
    }
}