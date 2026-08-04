package com.pinsoftstaj.bookstore_api.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Product name cannot be blank")
        @Size(
                max = 200,
                message = "Product name cannot exceed 200 characters"
        )
        String name,

        @NotNull(message = "Price cannot be null")
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = "Price must be greater than zero"
        )
        BigDecimal price,

        @Size(
                max = 5000,
                message = "Explanation cannot exceed 5000 characters"
        )
        String explanation,

        String base64Image,

        @NotNull(message = "Category must be selected")
        Long categoryId

) {
}