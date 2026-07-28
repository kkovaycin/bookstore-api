package com.pinsoftstaj.bookstore_api.dto.book;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

public record BookRequest(

        @NotBlank(message = "Kitap adı boş bırakılamaz")
        @Size(
                max = 200,
                message = "Kitap adı en fazla 200 karakter olabilir"
        )
        String title,

        @NotBlank(message = "ISBN boş bırakılamaz")
        @Size(
                max = 20,
                message = "ISBN en fazla 20 karakter olabilir"
        )
        String isbn,

        @NotNull(message = "Fiyat boş bırakılamaz")
        @DecimalMin(
                value = "0.01",
                message = "Fiyat en az 0.01 olmalıdır"
        )
        BigDecimal price,

        @NotNull(message = "Stok boş bırakılamaz")
        @Min(
                value = 0,
                message = "Stok negatif olamaz"
        )
        Integer stock,

        @NotNull(message = "Kategori seçilmelidir")
        @Positive(message = "Kategori kimliği pozitif olmalıdır")
        Long categoryId,

        @NotEmpty(message = "En az bir yazar seçilmelidir")
        Set<@Positive Long> authorIds

) {
}
