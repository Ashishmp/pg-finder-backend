package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.CreateUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.LoginUserRequest;
import com.pgfinder.pg_finder_backend.dto.request.RefreshTokenRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateUserRequest;
import com.pgfinder.pg_finder_backend.dto.response.LoginResponse;
import com.pgfinder.pg_finder_backend.dto.response.UserResponse;
import com.pgfinder.pg_finder_backend.entity.RefreshToken;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.repository.RefreshTokenRepository;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.security.jwt.JwtService;
import com.pgfinder.pg_finder_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private RefreshTokenRepository refreshTokenRepository;

    public UserServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
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
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setRole(savedUser.getRole());
        return response;
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found"));
        // Check if email is changing
        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User saved = userRepository.save(user);
        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setPhone(saved.getPhone());
        response.setRole(saved.getRole());
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
        refreshTokenRepository.deleteByUserId(user.getId());

        // Create new refresh token
        String refreshTokenValue = java.util.UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7)); // 7 days

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        // Build response
        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwt);
        response.setRefreshToken(savedRefreshToken.getToken());
        response.setExpiresIn(jwtService.getExpiration());
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }


    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new BusinessException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Refresh token expired");
        }

        User user = refreshToken.getUser();
        String newJwt = jwtService.generateToken(user.getId(), user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setAccessToken(newJwt);
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setId(user.getId());

        return response;
    }



}
