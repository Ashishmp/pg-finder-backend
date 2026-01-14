package com.pgfinder.pg_finder_backend.config;

import com.pgfinder.pg_finder_backend.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // ======================
                        // AUTH (Public)
                        // ======================
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login"
                        ).permitAll()

                        // ======================
                        // Swagger (Public)
                        // ======================
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ======================
                        // Public PG & Room browsing
                        // ======================
                        .requestMatchers(HttpMethod.GET, "/api/v1/pgs/**").permitAll()

                        // ======================
                        // Room Management (OWNER only)
                        // ======================
                        .requestMatchers(HttpMethod.POST, "/api/v1/pgs/*/rooms").hasRole("PG_OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/rooms/**").hasRole("PG_OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/rooms/**").hasRole("PG_OWNER")

                        // ======================
                        // Owner APIs
                        // ======================
                        .requestMatchers("/api/v1/owners/**").hasRole("PG_OWNER")

                        // ======================
                        // Admin APIs
                        // ======================
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                        // ======================
                        //Booking APIs
                        // ======================
                        .requestMatchers("/api/v1/bookings/me").hasRole("USER")
                        .requestMatchers("/api/v1/bookings/owner").hasRole("PG_OWNER")
                        .requestMatchers("/api/v1/bookings/**").authenticated()


                        // ======================
                        // Everything else
                        // ======================
                        .anyRequest().authenticated()

                )

                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // Required for authentication manager (login API)
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        );
    }
}
