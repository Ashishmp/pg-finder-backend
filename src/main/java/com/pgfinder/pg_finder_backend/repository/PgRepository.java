package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Pg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PgRepository extends JpaRepository<Pg, Long> {
    boolean existsByPgNameAndPgAddress(String pgName, String pgAddress);
    List<Pg> findByOwnerId(Long ownerId);
    Optional<Pg> findByIdAndOwnerId(Long pgId, Long ownerId);

}
