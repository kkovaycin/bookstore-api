package com.pinsoftstaj.bookstore_api.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Ürün adı boş bırakılamaz")
        @Size(
                max = 200,
                message = "Ürün adı en fazla 200 karakter olabilir"
        )
        String name,

        @NotNull(message = "Fiyat boş bırakılamaz")
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = "Fiyat sıfırdan büyük olmalıdır"
        )
        BigDecimal price,

        @Size(
                max = 5000,
                message = "Açıklama en fazla 5000 karakter olabilir"
        )
        String explanation,

        @NotNull(message = "Kategori seçilmelidir")
        Long categoryId

) {
}
