package com.pinsoftstaj.bookstore_api.service;

import com.pinsoftstaj.bookstore_api.dto.auth.LoginRequest;
import com.pinsoftstaj.bookstore_api.dto.auth.LoginResponse;
import com.pinsoftstaj.bookstore_api.dto.auth.RegisterRequest;
import com.pinsoftstaj.bookstore_api.dto.auth.UserResponse;
import com.pinsoftstaj.bookstore_api.entity.AppUser;
import com.pinsoftstaj.bookstore_api.entity.Role;
import com.pinsoftstaj.bookstore_api.exception.DuplicateResourceException;
import com.pinsoftstaj.bookstore_api.exception.ResourceNotFoundException;
import com.pinsoftstaj.bookstore_api.repository.AppUserRepository;
import com.pinsoftstaj.bookstore_api.repository.RoleRepository;
import com.pinsoftstaj.bookstore_api.security.DatabaseUserDetailsService;
import com.pinsoftstaj.bookstore_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DatabaseUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthService(
            AppUserRepository appUserRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            DatabaseUserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
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

        if (
                appUserRepository
                        .existsByEmailIgnoreCase(normalizedEmail)
        ) {
            throw new DuplicateResourceException(
                    "This email address is already in use"
            );
        }

        if (
                appUserRepository
                        .existsByUsernameIgnoreCase(normalizedUsername)
        ) {
            throw new DuplicateResourceException(
                    "This username is already in use"
            );
        }

        Role userRole = roleRepository
                .findByNameIgnoreCase("USER")
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "USER role was not found in the database"
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

    public LoginResponse login(
            LoginRequest request
    ) {
        String normalizedEmail =
                request.email()
                        .trim()
                        .toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        normalizedEmail,
                        request.password()
                )
        );

        AppUser user = appUserRepository
                .findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        normalizedEmail
                );

        String token =
                jwtService.generateToken(userDetails);

        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getExpiration(),
                UserResponse.from(user)
        );
    }
}