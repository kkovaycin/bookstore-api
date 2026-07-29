package com.pinsoftstaj.bookstore_api.dto.auth;

import com.pinsoftstaj.bookstore_api.entity.AppUser;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String role
) {

    public static UserResponse from(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getName()
        );
    }
}