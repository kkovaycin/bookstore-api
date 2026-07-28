package com.pinsoftstaj.bookstore_api.repository;

import com.pinsoftstaj.bookstore_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository
        extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(
            String isbn,
            Long id
    );

    List<Book> findByTitleContainingIgnoreCase(
            String title
    );

    List<Book> findByCategoryId(
            Long categoryId
    );

    List<Book>
    findByTitleContainingIgnoreCaseAndCategoryId(
            String title,
            Long categoryId
    );
}