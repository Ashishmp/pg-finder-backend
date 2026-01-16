package com.pgfinder.pg_finder_backend.repository;

import com.pgfinder.pg_finder_backend.entity.Booking;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByBooking(Booking booking);

    List<Review> findByPg(Pg pg);
}
