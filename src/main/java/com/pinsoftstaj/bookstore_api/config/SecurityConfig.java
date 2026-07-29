package com.pinsoftstaj.bookstore_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Herkes erişebilir
                        .requestMatchers(
                                "/api/health",
                                "/api/auth/register"
                        )
                        .permitAll()

                        // Ürünleri ve kategorileri herkes listeleyebilir
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/products/**",
                                "/api/categories/**"
                        )
                        .permitAll()

                        // Ürün ve kategori eklemek için ADMIN gerekir
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/products/**",
                                "/api/categories/**"
                        )
                        .hasRole("ADMIN")

                        // Ürün ve kategori güncellemek için ADMIN gerekir
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/products/**",
                                "/api/categories/**"
                        )
                        .hasRole("ADMIN")

                        // Ürün ve kategori silmek için ADMIN gerekir
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/products/**",
                                "/api/categories/**"
                        )
                        .hasRole("ADMIN")

                        // Yukarıdakiler dışındaki isteklerde giriş gerekir
                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(Customizer.withDefaults())

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
