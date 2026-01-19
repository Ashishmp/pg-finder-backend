package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.request.ChangePasswordRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateProfileRequest;
import com.pgfinder.pg_finder_backend.dto.response.UserProfileResponse;
import com.pgfinder.pg_finder_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



    @RestController
    @RequestMapping("/api/users/me")
    public class ProfileController {

        private final UserService userService;

        public ProfileController(UserService userService) {
            this.userService = userService;
        }

        // ======================
        // View own profile
        // ======================
        @GetMapping
        public ResponseEntity<ApiResponse<UserProfileResponse>> me() {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Profile fetched successfully",
                            userService.getMyProfile()
                    )
            );
        }

        // ======================
        // Update profile
        // ======================
        @PutMapping
        public ResponseEntity<ApiResponse<UserProfileResponse>> update(
                @RequestBody UpdateProfileRequest request) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Profile updated successfully",
                            userService.updateProfile(request)
                    )
            );
        }

        // ======================
        // Change password
        // ======================
        @PatchMapping("/password")
        public ResponseEntity<ApiResponse<Object>> changePassword(
                @RequestBody ChangePasswordRequest request) {

            userService.changePassword(request);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Password updated successfully", null)
            );
        }
    }
