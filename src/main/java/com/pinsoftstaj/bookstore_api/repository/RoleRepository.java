package com.pinsoftstaj.bookstore_api.repository;

import com.pinsoftstaj.bookstore_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<Role, Long> {

    Optional<Role> findByNameIgnoreCase(String name);
}