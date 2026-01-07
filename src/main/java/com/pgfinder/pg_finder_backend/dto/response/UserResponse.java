package com.pgfinder.pg_finder_backend.dto.response;

public class UserResponse {
    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private Long pgId;
    private String name;
    private String email;
    private String phone;
    private String role;

}
