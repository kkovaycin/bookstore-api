package com.pinsoftstaj.bookstore_api.dto.book;

import com.pinsoftstaj.bookstore_api.dto.author.AuthorResponse;
import com.pinsoftstaj.bookstore_api.entity.Book;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record BookResponse(
        Long id,
        String title,
        String isbn,
        BigDecimal price,
        Integer stock,
        Long categoryId,
        String categoryName,
        Set<AuthorResponse> authors
) {

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPrice(),
                book.getStock(),
                book.getCategory().getId(),
                book.getCategory().getName(),
                book.getAuthors()
                        .stream()
                        .map(AuthorResponse::from)
                        .collect(Collectors.toSet())
        );
    }
}
