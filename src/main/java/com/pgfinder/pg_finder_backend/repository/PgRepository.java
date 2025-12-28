package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Pg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PgRepository extends JpaRepository<Pg, Long> {
    boolean existsByPgNameAndPgAddress(String pgName, String pgAddress);
}
