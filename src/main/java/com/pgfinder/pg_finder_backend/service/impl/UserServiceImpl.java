package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.*;
//import com.pgfinder.pg_finder_backend.dto.request.RefreshTokenRequest;
import com.pgfinder.pg_finder_backend.dto.response.LoginResponse;
import com.pgfinder.pg_finder_backend.dto.response.UserProfileResponse;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;
import com.pgfinder.pg_finder_backend.entity.RefreshToken;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.Role;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.mapper.UserMapper;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.security.jwt.JwtService;
import com.pgfinder.pg_finder_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
//    private RefreshTokenRepository refreshTokenRepository;

    public UserServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository
//            RefreshTokenRepository refreshTokenRepository
            ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
//        this.refreshTokenRepository = refreshTokenRepository;
    }
    @Override
    public UserResponse createUser(CreateUserRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException("Email already exists");
        }
        if(userRepository.existsByPhone(request.getPhone())){
            throw new BusinessException("Number already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setRole(savedUser.getRole().name());
        return response;
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found"));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException("Current password is incorrect");
        }
        user.setEmail(request.getEmail());

        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        User saved = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setPhone(saved.getPhone());
        response.setRole(user.getRole().name());

        return response;
    }

    @Override
    public void logout(HttpServletRequest request) {

    }


    @Override
    public LoginResponse loginUser(LoginUserRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("User not found"));

        // Generate JWT
        String jwt = jwtService.generateToken(user.getId(), user.getEmail());

        // Remove old refresh tokens
//        refreshTokenRepository.deleteByUserId(user.getId());

        // Create new refresh token
        String refreshTokenValue = java.util.UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7)); // 7 days

//        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        // Build response
        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwt);
//        response.setRefreshToken(savedRefreshToken.getToken());
        response.setExpiresIn(jwtService.getExpiration());
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());

        return response;
    }


//    @Override
//    public LoginResponse refreshToken(RefreshTokenRequest request) {
//
//        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
//                .orElseThrow(() -> new BusinessException("Invalid refresh token"));
//
//        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
//            throw new BusinessException("Refresh token expired");
//        }
//
//        User user = refreshToken.getUser();
//        String newJwt = jwtService.generateToken(user.getId(), user.getEmail());
//
//        LoginResponse response = new LoginResponse();
//        response.setAccessToken(newJwt);
//        response.setEmail(user.getEmail());
//        response.setRole(user.getRole());
//        response.setId(user.getId());
//
//        return response;
//    }


    @Service
    public static class TokenBlacklistService {

        private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

        public void blacklist(String token) {
            blacklistedTokens.add(token);
        }

        public boolean isBlacklisted(String token) {
            return blacklistedTokens.contains(token);
        }
    }
    @Override
    public UserProfileResponse getMyProfile() {
        User user = getCurrentUser();
        return UserMapper.toProfile(user);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {

        User user = getCurrentUser();

        user.setName(request.getName());
        user.setPhone(request.getPhone());

        return UserMapper.toProfile(userRepository.save(user));
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {

        User user = getCurrentUser();

        if (!passwordEncoder.matches(
                request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
