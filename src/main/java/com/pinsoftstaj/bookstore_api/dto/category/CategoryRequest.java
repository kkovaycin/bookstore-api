package com.pinsoftstaj.bookstore_api.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

        @NotBlank(message = "Kategori adı boş bırakılamaz")
        @Size(
                max = 100,
                message = "Kategori adı en fazla 100 karakter olabilir"
        )
        String name

) {
}
