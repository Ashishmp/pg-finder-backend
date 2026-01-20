package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.enums.PgStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PgRepository extends JpaRepository<Pg, Long>, JpaSpecificationExecutor<Pg> {
    boolean existsByPgNameAndPgAddress(String pgName, String pgAddress);
    List<Pg> findByOwnerId(Long ownerId);
    Optional<Pg> findByIdAndOwnerId(Long pgId, Long ownerId);
    List<Pg> findByStatus(PgStatus status);

}
