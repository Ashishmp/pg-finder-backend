package com.pgfinder.pg_finder_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/users/register","/api/v1/users/update/{id}","/api/v1/users/login","/api/v1/pgs/create","/api/v1/pgs/viewAll","/api/v1/pgs/{pgId}").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(httpbasic -> httpbasic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
//,"/api/v1/pgs/create"