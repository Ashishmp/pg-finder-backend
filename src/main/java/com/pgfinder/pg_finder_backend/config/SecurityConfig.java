package com.pgfinder.pg_finder_backend.config;

import com.pgfinder.pg_finder_backend.filter.RateLimitFilter;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RateLimitFilter rateLimitFilter;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, RateLimitFilter rateLimitFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.rateLimitFilter = rateLimitFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // ======================
                        // AUTH (Public)
                        // ======================
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login"
                        ).permitAll()
                        // ======================
                        // Public Health Check
                        // ======================

                        .requestMatchers("/api/public/health-check").permitAll()

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

                        // Full PG details (needs auth)
                                .requestMatchers(HttpMethod.GET, "/api/v1/pgs/*/full")
                                .hasAnyRole("USER", "OWNER", "ADMIN")


                                // Public PG browsing
                        .requestMatchers(HttpMethod.GET, "/api/v1/pgs/**")
                        .permitAll()

                        // ======================
                        // Public PG & Room browsing
                        // ======================
//                        .requestMatchers(HttpMethod.GET, "/api/v1/pgs/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/pgs/*/full")
//                        .hasAnyRole("USER", "OWNER", "ADMIN")

                        // ======================
                        // Room Management (OWNER only)
                        // ======================
                        .requestMatchers(HttpMethod.POST, "/api/v1/pgs/*/rooms").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/rooms/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/rooms/**").hasRole("OWNER")

                        // ======================
                        // Owner APIs
                        // ======================
                        .requestMatchers("/api/v1/owners/**").hasRole("OWNER")

                        // ======================
                        // Admin APIs
                        // ======================
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                        // ======================
                        //Booking APIs
                        // ======================
                        .requestMatchers("/api/v1/bookings/me").hasRole("USER")
                        .requestMatchers("/api/v1/bookings/owner").hasRole("OWNER")
                        .requestMatchers("/api/v1/bookings/**").authenticated()

                        // ======================
                        // Amenities APIs
                        // ======================

                        .requestMatchers("/api/amenities").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/pgs/*/amenities").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/pgs/*/rules").hasRole("OWNER")

                        // ======================
                        // Analytics APIs
                        // ======================
                        .requestMatchers("/api/v1/analytics/owner/**").hasRole("OWNER")

                        // =====================
                        // Payment simultion
                        // =====================

                        .requestMatchers("/api/v1/payments/**")
                        .hasRole("ADMIN")

                        // ======================
                        // Everything else
                        // ======================
                        .anyRequest().authenticated()

                )

                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())



                .addFilterBefore(
                        rateLimitFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
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
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",   // React local
                "http://localhost:5173"    // Vite
        ));

        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of(
                "Authorization", "Content-Type"
        ));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
