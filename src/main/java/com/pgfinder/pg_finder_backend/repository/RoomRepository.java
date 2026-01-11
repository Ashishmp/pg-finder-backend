package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // For owner inventory
    List<Room> findByPg(Pg pg);

    // Public listing (only sellable inventory)
    List<Room> findByPgAndStatusAndAvailableBedsGreaterThan(
            Pg pg,
            RoomStatus status,
            Integer beds
    );

    // Prevent duplicate room types
    Optional<Room> findByPgAndSharingTypeAndIsAc(Pg pg, Integer sharingType, Boolean isAc);
}
