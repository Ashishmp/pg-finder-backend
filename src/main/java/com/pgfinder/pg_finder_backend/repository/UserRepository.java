package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
