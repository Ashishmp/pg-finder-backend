package com.pgfinder.pg_finder_backend.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static Long getUserId() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof CustomUserDetails user) {
            return user.getId();
        }

        throw new RuntimeException("Unauthorized");
    }

    public static String getEmail() {
        return ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();
    }
}
