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
                                "Ürün bulunamadı. ID: " + id
                        )
                );
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Kategori bulunamadı. ID: " + id
                        )
                );
    }

    private String normalizeExplanation(
            String explanation
    ) {
        if (explanation == null
                || explanation.isBlank()) {
            return null;
        }

        return explanation.trim();
    }
}