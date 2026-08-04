package com.pinsoftstaj.bookstore_api.dto.auth;

import com.pinsoftstaj.bookstore_api.entity.AppUser;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String role
) {

    public static UserResponse from(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }
}