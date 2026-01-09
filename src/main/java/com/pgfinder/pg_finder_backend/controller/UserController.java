package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.request.CreateUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.LoginUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateUserRequest;
import com.pgfinder.pg_finder_backend.dto.response.LoginResponse;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;
import com.pgfinder.pg_finder_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginUserRequest request){
        LoginResponse response = userService.loginUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id ,@Valid @RequestBody UpdateUserRequest request){
//        UserResponse response = userService.updateUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, request));
    }
}
