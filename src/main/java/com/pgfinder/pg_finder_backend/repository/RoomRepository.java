package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    long countByPg_Owner_Id(Long ownerId);

    @Query("""
        SELECT COALESCE(SUM(r.totalBeds),0)
        FROM Room r
        WHERE r.pg.owner.id = :ownerId
    """)
    long totalBeds(@Param("ownerId") Long ownerId);


}
