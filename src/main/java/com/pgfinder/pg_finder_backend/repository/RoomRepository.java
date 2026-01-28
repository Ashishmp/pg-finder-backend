package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoomRepository
        extends JpaRepository<Room, Long>,
        JpaSpecificationExecutor<Room> {

    /**
     * Business rule:
     * Prevent duplicate room for same PG + sharingType + AC
     */
    boolean existsByPgIdAndSharingTypeAndAc(
            Long pgId,
            Integer sharingType,
            Boolean ac
    );
}
