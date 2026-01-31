package com.pgfinder.pg_finder_backend.repository.analytics;

import com.pgfinder.pg_finder_backend.entity.Pg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OwnerDashboardRepository extends JpaRepository<Pg, Long> {

    // -------- Portfolio --------

    @Query("""
        SELECT COUNT(p)
        FROM Pg p
        WHERE p.owner.id = :ownerId
    """)
    long countPgs(@Param("ownerId") Long ownerId);

    @Query("""
        SELECT COUNT(r)
        FROM Room r
        JOIN r.pg p
        WHERE p.owner.id = :ownerId
    """)
    long countRooms(@Param("ownerId") Long ownerId);

    // -------- Bookings --------

    @Query("""
        SELECT COUNT(b)
        FROM Booking b
        JOIN b.pg p
        WHERE p.owner.id = :ownerId
    """)
    long totalBookings(@Param("ownerId") Long ownerId);

    @Query("""
        SELECT COUNT(b)
        FROM Booking b
        JOIN b.pg p
        WHERE p.owner.id = :ownerId
          AND b.createdAt >= :startOfMonth
    """)
    long monthlyBookings(
            @Param("ownerId") Long ownerId,
            @Param("startOfMonth") LocalDateTime startOfMonth
    );
    @Query("""
    SELECT COALESCE(SUM(pay.amount), 0)
    FROM Payment pay
    JOIN pay.booking b
    JOIN b.pg p
    WHERE p.owner.id = :ownerId
      AND pay.status = 'SUCCESS'
      AND pay.createdAt >= :startOfMonth
""")
    double monthlyRevenue(
            @Param("ownerId") Long ownerId,
            @Param("startOfMonth") LocalDateTime startOfMonth
    );



    @Query("""
        SELECT COUNT(b)
        FROM Booking b
        JOIN b.pg p
        WHERE p.owner.id = :ownerId
          AND b.status = 'ACTIVE'
    """)
    long activeBookings(@Param("ownerId") Long ownerId);

    @Query("""
        SELECT COUNT(b)
        FROM Booking b
        JOIN b.pg p
        WHERE p.owner.id = :ownerId
          AND b.status = 'COMPLETED'
    """)
    long completedBookings(@Param("ownerId") Long ownerId);

    // -------- Revenue --------

    @Query("""
        SELECT COALESCE(SUM(pay.amount), 0)
        FROM Payment pay
        JOIN pay.booking b
        JOIN b.pg p
        WHERE p.owner.id = :ownerId
          AND pay.status = 'SUCCESS'
    """)
    double totalRevenue(@Param("ownerId") Long ownerId);



    // -------- Ratings --------

    @Query("""
        SELECT ROUND(AVG(r.rating), 1)
        FROM Review r
        JOIN r.pg p
        WHERE p.owner.id = :ownerId
    """)
    Double averageRating(@Param("ownerId") Long ownerId);

    @Query("""
        SELECT COUNT(r)
        FROM Review r
        JOIN r.pg p
        WHERE p.owner.id = :ownerId
    """)
    long totalReviews(@Param("ownerId") Long ownerId);
}
