package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreateUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.LoginUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateUserRequest;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);
    UserResponse loginUser(LoginUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
}
