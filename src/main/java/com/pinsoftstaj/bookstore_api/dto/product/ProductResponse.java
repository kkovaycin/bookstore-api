package com.pinsoftstaj.bookstore_api.dto.product;

import com.pinsoftstaj.bookstore_api.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        String explanation,
        Long categoryId,
        String categoryName
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getExplanation(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}
