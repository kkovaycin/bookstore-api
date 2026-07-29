package com.pinsoftstaj.bookstore_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Ad boş bırakılamaz")
        @Size(max = 100, message = "Ad en fazla 100 karakter olabilir")
        String firstName,

        @NotBlank(message = "Soyad boş bırakılamaz")
        @Size(max = 100, message = "Soyad en fazla 100 karakter olabilir")
        String lastName,

        @NotBlank(message = "Kullanıcı adı boş bırakılamaz")
        @Size(
                min = 3,
                max = 100,
                message = "Kullanıcı adı 3 ile 100 karakter arasında olmalıdır"
        )
        String username,

        @NotBlank(message = "E-posta boş bırakılamaz")
        @Email(message = "Geçerli bir e-posta adresi girilmelidir")
        String email,

        @NotBlank(message = "Şifre boş bırakılamaz")
        @Size(
                min = 8,
                max = 100,
                message = "Şifre 8 ile 100 karakter arasında olmalıdır"
        )
        String password

) {
}