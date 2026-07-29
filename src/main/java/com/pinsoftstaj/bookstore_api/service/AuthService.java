package com.pinsoftstaj.bookstore_api.service;

import com.pinsoftstaj.bookstore_api.dto.auth.RegisterRequest;
import com.pinsoftstaj.bookstore_api.dto.auth.UserResponse;
import com.pinsoftstaj.bookstore_api.entity.AppUser;
import com.pinsoftstaj.bookstore_api.entity.Role;
import com.pinsoftstaj.bookstore_api.exception.DuplicateResourceException;
import com.pinsoftstaj.bookstore_api.exception.ResourceNotFoundException;
import com.pinsoftstaj.bookstore_api.repository.AppUserRepository;
import com.pinsoftstaj.bookstore_api.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AppUserRepository appUserRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(
            RegisterRequest request
    ) {
        String normalizedEmail =
                request.email()
                        .trim()
                        .toLowerCase();

        String normalizedUsername =
                request.username()
                        .trim()
                        .toLowerCase();

        if (appUserRepository
                .existsByEmailIgnoreCase(normalizedEmail)) {

            throw new DuplicateResourceException(
                    "Bu e-posta adresi zaten kullanılıyor"
            );
        }

        if (appUserRepository
                .existsByUsernameIgnoreCase(normalizedUsername)) {

            throw new DuplicateResourceException(
                    "Bu kullanıcı adı zaten kullanılıyor"
            );
        }

        Role userRole = roleRepository
                .findByNameIgnoreCase("USER")
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "USER rolü veritabanında bulunamadı"
                        )
                );

        AppUser user = new AppUser(
                request.firstName().trim(),
                request.lastName().trim(),
                normalizedUsername,
                normalizedEmail,
                passwordEncoder.encode(
                        request.password()
                ),
                userRole
        );

        AppUser savedUser =
                appUserRepository.save(user);

        return UserResponse.from(savedUser);
    }
}