package com.pgfinder.pg_finder_backend.dto.request;

public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

