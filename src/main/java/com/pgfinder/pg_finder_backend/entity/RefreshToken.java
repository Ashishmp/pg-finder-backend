package com.pgfinder.pg_finder_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 300)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public Long getId() { return id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public void setId(Long id) {
        this.id = id;
    }
}



//{
//        "name": "Admin",
//        "email": "admin@test.com",
//        "password": "Admin@1234",
//        "phone": "9000000003"
//        }



//{
//        "name": "PG Owner",
//        "email": "owner@test.com",
//        "password": "Owner@1234",
//        "phone": "9000000002"
//        }
//
//{
//        "name": "Normal User",
//        "email": "user@test.com",
//        "password": "User@1234",
//        "phone": "9000000001"
//        }
