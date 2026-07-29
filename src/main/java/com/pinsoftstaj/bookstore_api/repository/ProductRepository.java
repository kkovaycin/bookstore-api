package com.pinsoftstaj.bookstore_api.repository;

import com.pinsoftstaj.bookstore_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Long> {
}