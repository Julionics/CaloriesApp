package com.example.caloriesapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout").permitAll() // Lejo qasje të lirë në login/logout
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Vetëm adminët
                        .requestMatchers("/food-entry/**").hasRole("USER") // Vetëm përdoruesit
                        .anyRequest().authenticated() // Çdo gjë tjetër kërkon autentifikim
                )
                .formLogin(form -> form
                        .loginPage("/login") // Faqja e personalizuar e login-it
                        .successHandler(this::loginSuccessHandler) // Trajto pas suksesit të login-it
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Endpoint për logout
                        .logoutSuccessUrl("/login?logout") // Kthehu në login pas logout-it
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/login?error=access-denied"); // Në rast të ndalimit të qasjes
                        })
                );

        return http.build();
    }

    private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        authentication.getAuthorities().forEach(authority -> {
            try {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    response.sendRedirect("/admin/food-management"); // Ridrejto adminët
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    response.sendRedirect("/food-entry/new"); // Ridrejto përdoruesit
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin@example.com")
                        .password(passwordEncoder().encode("adminpass")) // Përdor fjalëkalim të koduar
                        .roles("ADMIN") // Roli admin
                        .build(),
                User.builder()
                        .username("user@example.com")
                        .password(passwordEncoder().encode("userpass")) // Përdor fjalëkalim të koduar
                        .roles("USER") // Roli user
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Kodimi i fjalëkalimeve
    }
}
