package com.pgfinder.pg_finder_backend.repository;


import com.pgfinder.pg_finder_backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomAnalyticsRepository extends JpaRepository<Room, Long> {

    long countByPgOwnerId(Long ownerId);

    @Query("""
        SELECT COALESCE(SUM(r.totalBeds), 0)
        FROM Room r
        WHERE r.pg.owner.id = :ownerId
    """)
    long totalBeds(@Param("ownerId") Long ownerId);
}
