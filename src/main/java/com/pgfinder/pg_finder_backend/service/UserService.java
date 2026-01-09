package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreateUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.LoginUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateUserRequest;
import com.pgfinder.pg_finder_backend.dto.response.LoginResponse;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);
    LoginResponse loginUser(LoginUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
}
