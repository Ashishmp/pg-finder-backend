package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Booking;
import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
