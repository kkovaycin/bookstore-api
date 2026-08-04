package com.pinsoftstaj.bookstore_api.dto.auth;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn,
        UserResponse user
) {
}