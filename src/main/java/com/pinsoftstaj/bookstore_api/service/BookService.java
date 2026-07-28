package com.pinsoftstaj.bookstore_api.service;

import com.pinsoftstaj.bookstore_api.dto.book.BookRequest;
import com.pinsoftstaj.bookstore_api.dto.book.BookResponse;
import com.pinsoftstaj.bookstore_api.entity.Author;
import com.pinsoftstaj.bookstore_api.entity.Book;
import com.pinsoftstaj.bookstore_api.entity.Category;
import com.pinsoftstaj.bookstore_api.exception.DuplicateResourceException;
import com.pinsoftstaj.bookstore_api.exception.ResourceNotFoundException;
import com.pinsoftstaj.bookstore_api.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public BookService(
            BookRepository bookRepository,
            CategoryService categoryService,
            AuthorService authorService
    ) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    public List<BookResponse> findAll(
            String title,
            Long categoryId
    ) {
        List<Book> books;

        boolean hasTitle =
                title != null && !title.isBlank();

        boolean hasCategory =
                categoryId != null;

        if (hasTitle && hasCategory) {

            books = bookRepository
                    .findByTitleContainingIgnoreCaseAndCategoryId(
                            title.trim(),
                            categoryId
                    );

        } else if (hasTitle) {

            books = bookRepository
                    .findByTitleContainingIgnoreCase(
                            title.trim()
                    );

        } else if (hasCategory) {

            books = bookRepository
                    .findByCategoryId(categoryId);

        } else {

            books = bookRepository.findAll();
        }

        return books.stream()
                .map(BookResponse::from)
                .toList();
    }

    public BookResponse findById(Long id) {
        return BookResponse.from(
                getEntity(id)
        );
    }

    @Transactional
    public BookResponse create(
            BookRequest request
    ) {
        String isbn = request.isbn().trim();

        if (bookRepository.existsByIsbn(isbn)) {
            throw new DuplicateResourceException(
                    "Bu ISBN ile kayıtlı kitap zaten var: "
                            + isbn
            );
        }

        Category category =
                categoryService.getEntity(
                        request.categoryId()
                );

        Set<Author> authors =
                authorService.getEntities(
                        request.authorIds()
                );

        Book book = new Book(
                request.title().trim(),
                isbn,
                request.price(),
                request.stock(),
                category,
                authors
        );

        Book savedBook =
                bookRepository.save(book);

        return BookResponse.from(savedBook);
    }

    @Transactional
    public BookResponse update(
            Long id,
            BookRequest request
    ) {
        Book book = getEntity(id);

        String isbn = request.isbn().trim();

        if (bookRepository
                .existsByIsbnAndIdNot(
                        isbn,
                        id
                )) {

            throw new DuplicateResourceException(
                    "Bu ISBN başka bir kitapta kullanılıyor: "
                            + isbn
            );
        }

        Category category =
                categoryService.getEntity(
                        request.categoryId()
                );

        Set<Author> authors =
                authorService.getEntities(
                        request.authorIds()
                );

        book.update(
                request.title().trim(),
                isbn,
                request.price(),
                request.stock(),
                category,
                authors
        );

        return BookResponse.from(book);
    }

    @Transactional
    public void delete(Long id) {
        Book book = getEntity(id);

        bookRepository.delete(book);
    }

    private Book getEntity(Long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Kitap bulunamadı. id=" + id
                        )
                );
    }
}