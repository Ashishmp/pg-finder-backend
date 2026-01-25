package com.pgfinder.pg_finder_backend.mapper;

import com.pgfinder.pg_finder_backend.dto.response.UserProfileResponse;
import com.pgfinder.pg_finder_backend.entity.User;

public class UserMapper {

    public static UserProfileResponse toProfile(User user) {

        UserProfileResponse dto = new UserProfileResponse();
        dto.setUserId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole().name());

        return dto;
    }
}
