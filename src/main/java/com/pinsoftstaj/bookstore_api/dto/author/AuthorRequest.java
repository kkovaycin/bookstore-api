package com.pinsoftstaj.bookstore_api.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorRequest(

        @NotBlank(
                message = "Yazar adı boş bırakılamaz"
        )
        @Size(
                max = 100,
                message = "Yazar adı en fazla 100 karakter olabilir"
        )
        String firstName,

        @NotBlank(
                message = "Yazar soyadı boş bırakılamaz"
        )
        @Size(
                max = 100,
                message = "Yazar soyadı en fazla 100 karakter olabilir"
        )
        String lastName

) {
}