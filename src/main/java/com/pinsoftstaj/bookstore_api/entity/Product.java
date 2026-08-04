package com.pinsoftstaj.bookstore_api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 200
    )
    private String name;

    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal price;

    @Column(
            columnDefinition = "TEXT"
    )
    private String explanation;

    @Column(
            name = "base64_image",
            columnDefinition = "TEXT"
    )
    private String base64Image;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_products_category"
            )
    )
    private Category category;

    protected Product() {
    }

    public Product(
            String name,
            BigDecimal price,
            String explanation,
            String base64Image,
            Category category
    ) {
        this.name = name;
        this.price = price;
        this.explanation = explanation;
        this.base64Image = base64Image;
        this.category = category;
    }

    public void update(
            String name,
            BigDecimal price,
            String explanation,
            String base64Image,
            Category category
    ) {
        this.name = name;
        this.price = price;
        this.explanation = explanation;
        this.base64Image = base64Image;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public Category getCategory() {
        return category;
    }
}