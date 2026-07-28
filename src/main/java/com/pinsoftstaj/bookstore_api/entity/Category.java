package com.pinsoftstaj.bookstore_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_categories_name",
                        columnNames = "name"
                )
        }
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 100
    )
    private String name;

    /*
     * JPA için gerekli parametresiz constructor.
     */
    protected Category() {
    }

    /*
     * Yeni kategori oluştururken kullanılır.
     * ID veritabanı tarafından otomatik oluşturulur.
     */
    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /*
     * PUT işlemi sırasında kategori adını günceller.
     */
    public void update(String name) {
        this.name = name;
    }
}