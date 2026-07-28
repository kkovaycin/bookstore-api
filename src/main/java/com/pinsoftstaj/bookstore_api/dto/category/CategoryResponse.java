package com.pinsoftstaj.bookstore_api.dto.category;

import com.pinsoftstaj.bookstore_api.entity.Category;

public record CategoryResponse(
        Long id,
        String name
) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}