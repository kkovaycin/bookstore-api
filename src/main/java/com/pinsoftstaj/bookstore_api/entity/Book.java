package com.pinsoftstaj.bookstore_api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
        name = "books",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_books_isbn",
                        columnNames = "isbn"
                )
        }
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 200
    )
    private String title;

    @Column(
            nullable = false,
            length = 20
    )
    private String isbn;

    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_books_category"
            )
    )
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = {
                    @JoinColumn(name = "book_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "author_id")
            }
    )
    private Set<Author> authors = new LinkedHashSet<>();

    protected Book() {
    }

    public Book(
            String title,
            String isbn,
            BigDecimal price,
            Integer stock,
            Category category,
            Set<Author> authors
    ) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.authors.addAll(authors);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Category getCategory() {
        return category;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void update(
            String title,
            String isbn,
            BigDecimal price,
            Integer stock,
            Category category,
            Set<Author> authors
    ) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
        this.category = category;

        this.authors.clear();
        this.authors.addAll(authors);
    }
}
