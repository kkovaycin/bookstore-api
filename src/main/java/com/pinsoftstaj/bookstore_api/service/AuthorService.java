package com.pinsoftstaj.bookstore_api.service;

import com.pinsoftstaj.bookstore_api.dto.author.AuthorRequest;
import com.pinsoftstaj.bookstore_api.dto.author.AuthorResponse;
import com.pinsoftstaj.bookstore_api.entity.Author;
import com.pinsoftstaj.bookstore_api.exception.ResourceNotFoundException;
import com.pinsoftstaj.bookstore_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(
            AuthorRepository authorRepository
    ) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorResponse> findAll() {
        return authorRepository
                .findAll()
                .stream()
                .map(AuthorResponse::from)
                .toList();
    }

    public AuthorResponse findById(Long id) {
        return AuthorResponse.from(
                getEntity(id)
        );
    }

    @Transactional
    public AuthorResponse create(
            AuthorRequest request
    ) {
        Author author = new Author(
                request.firstName().trim(),
                request.lastName().trim()
        );

        return AuthorResponse.from(
                authorRepository.save(author)
        );
    }

    @Transactional
    public AuthorResponse update(
            Long id,
            AuthorRequest request
    ) {
        Author author = getEntity(id);

        author.update(
                request.firstName().trim(),
                request.lastName().trim()
        );

        return AuthorResponse.from(author);
    }

    @Transactional
    public void delete(Long id) {
        Author author = getEntity(id);

        authorRepository.delete(author);
    }

    public Author getEntity(Long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Yazar bulunamadı. id=" + id
                        )
                );
    }

    public Set<Author> getEntities(
            Set<Long> authorIds
    ) {
        List<Author> authors =
                authorRepository.findAllById(authorIds);

        if (authors.size() != authorIds.size()) {
            throw new ResourceNotFoundException(
                    "Bir veya daha fazla yazar bulunamadı"
            );
        }

        return new LinkedHashSet<>(authors);
    }
}
