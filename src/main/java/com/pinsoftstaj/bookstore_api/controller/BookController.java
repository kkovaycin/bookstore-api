package com.pinsoftstaj.bookstore_api.controller;

import com.pinsoftstaj.bookstore_api.dto.book.BookRequest;
import com.pinsoftstaj.bookstore_api.dto.book.BookResponse;
import com.pinsoftstaj.bookstore_api.service.BookService;
//import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Tüm kitapları listeler.
    // Örnek: GET /api/books
    // Örnek: GET /api/books?title=Tutunamayanlar
    // Örnek: GET /api/books?categoryId=1
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId
    ) {
        List<BookResponse> books =
                bookService.findAll(title, categoryId);

        return ResponseEntity.ok(books);
    }

    // ID ile tek bir kitabı getirir.
    // Örnek: GET /api/books/1
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(
            @PathVariable Long id
    ) {
        BookResponse book =
                bookService.findById(id);

        return ResponseEntity.ok(book);
    }

    // Yeni kitap oluşturur.
    // Örnek: POST /api/books
    @PostMapping
    public ResponseEntity<BookResponse> createBook(
             @RequestBody BookRequest request
    ) {
        BookResponse createdBook =
                bookService.create(request);

        URI location = URI.create(
                "/api/books/" + createdBook.id()
        );

        return ResponseEntity
                .created(location)
                .body(createdBook);
    }

    // Mevcut kitabın bütün alanlarını günceller.
    // Örnek: PUT /api/books/1
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
             @RequestBody BookRequest request
    ) {
        BookResponse updatedBook =
                bookService.update(id, request);

        return ResponseEntity.ok(updatedBook);
    }

    // Kitabı siler.
    // Örnek: DELETE /api/books/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable Long id
    ) {
        bookService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
