package com.pinsoftstaj.bookstore_api.dto.author;

import com.pinsoftstaj.bookstore_api.entity.Author;

public record AuthorResponse(
        Long id,
        String firstName,
        String lastName
) {

    public static AuthorResponse from(
            Author author
    ) {
        return new AuthorResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName()
        );
    }
}
