package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.request.CreateUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.LoginUserRequest;
//import com.pgfinder.pg_finder_backend.dto.request.RefreshTokenRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateUserRequest;
import com.pgfinder.pg_finder_backend.dto.response.LoginResponse;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;
import com.pgfinder.pg_finder_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")

public class UserController{
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginUserRequest request) {
        LoginResponse response = userService.loginUser(request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", response)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User updated successfully", response)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Logged out successfully", "OK")
        );
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@RequestBody RefreshTokenRequest request) {
//
//        LoginResponse response = userService.refreshToken(request);
//
//        return ResponseEntity.ok(
//                new ApiResponse<>(true, "Token refreshed", response)
//        );
//    }

}
