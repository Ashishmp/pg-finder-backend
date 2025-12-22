package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreateUserRequest;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);
}
