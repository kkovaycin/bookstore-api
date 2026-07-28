package com.pinsoftstaj.bookstore_api.controller;

import com.pinsoftstaj.bookstore_api.dto.author.AuthorRequest;
import com.pinsoftstaj.bookstore_api.dto.author.AuthorResponse;
import com.pinsoftstaj.bookstore_api.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(
            AuthorService authorService
    ) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>>
    getAllAuthors() {

        return ResponseEntity.ok(
                authorService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse>
    getAuthorById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                authorService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<AuthorResponse>
    createAuthor(
            @Valid
            @RequestBody
            AuthorRequest request
    ) {
        AuthorResponse createdAuthor =
                authorService.create(request);

        URI location = URI.create(
                "/api/authors/"
                        + createdAuthor.id()
        );

        return ResponseEntity
                .created(location)
                .body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse>
    updateAuthor(
            @PathVariable Long id,

            @Valid
            @RequestBody
            AuthorRequest request
    ) {
        return ResponseEntity.ok(
                authorService.update(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteAuthor(
            @PathVariable Long id
    ) {
        authorService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}