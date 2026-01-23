package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Booking;
import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // All bookings of a user
    List<Booking> findByUser(User user);

    // All bookings for a PG (owner view)
    List<Booking> findByPgId(Long pgId);

    // Active bookings for a room (used to prevent overbooking)
    List<Booking> findByRoomAndStatusIn(Room room, List<BookingStatus> statuses);

    // Check if user already has active booking in this PG
    boolean existsByUserAndPgAndStatusIn(
            User user,
            com.pgfinder.pg_finder_backend.entity.Pg pg,
            List<BookingStatus> statuses
    );

    // Date overlap check (future-ready)
    List<Booking> findByRoomAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Room room,
            List<BookingStatus> statuses,
            LocalDate endDate,
            LocalDate startDate
    );
    List<Booking> findByPgOwner(User owner);
    @Query("""
    SELECT COUNT(b)
    FROM Booking b
    WHERE b.pg.owner.id = :ownerId
    AND b.status IN ('CONFIRMED','CHECKED_IN','COMPLETED')
""")
    long countActiveBookings(@Param("ownerId") Long ownerId);


    @Query("""
    SELECT COALESCE(SUM(b.price),0)
    FROM Booking b
    WHERE b.pg.owner.id = :ownerId
    AND b.status IN ('CONFIRMED','CHECKED_IN','COMPLETED')
""")
    double totalRevenue(@Param("ownerId") Long ownerId);

}
