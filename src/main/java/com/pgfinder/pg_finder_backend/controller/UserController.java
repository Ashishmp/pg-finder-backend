package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.request.CreateUserRequest;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;
import com.pgfinder.pg_finder_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")

public class UserController{
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request){
        UserResponse response = userService.createUser(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
