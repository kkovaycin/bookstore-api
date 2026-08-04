package com.pinsoftstaj.bookstore_api.repository;

import com.pinsoftstaj.bookstore_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(
            String name
    );

    List<Product> findByCategoryIdIn(
            List<Long> categoryIds
    );

    @Query("""
            SELECT DISTINCT product
            FROM Product product
            WHERE LOWER(product.name)
                  LIKE LOWER(CONCAT('%', :name, '%'))
               OR product.category.id IN :categoryIds
            """)
    List<Product> findByNameOrCategoryIds(
            @Param("name") String name,
            @Param("categoryIds") List<Long> categoryIds
    );
}