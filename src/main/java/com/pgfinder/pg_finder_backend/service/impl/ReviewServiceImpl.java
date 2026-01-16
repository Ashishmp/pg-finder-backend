package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.entity.*;
import com.pgfinder.pg_finder_backend.enums.BookingStatus;
import com.pgfinder.pg_finder_backend.repository.*;
import com.pgfinder.pg_finder_backend.service.ReviewService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final PgRepository pgRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            BookingRepository bookingRepository,
            PgRepository pgRepository,
            UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.pgRepository = pgRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ===============================
    // USER posts review
    // ===============================
    @Override
    @Transactional
    public Review createReview(Long bookingId, Integer rating, String comment) {

        User user = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Must be user's booking
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("This booking does not belong to you");
        }

        // Must be completed stay
        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new RuntimeException("You can only review after completing your stay");
        }

        // Prevent multiple reviews
        if (reviewRepository.existsByBooking(booking)) {
            throw new RuntimeException("You already reviewed this booking");
        }

        Pg pg = booking.getPg();

        // Owner cannot review own PG
        if (pg.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot review your own PG");
        }

        // Rating validation
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setPg(pg);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        reviewRepository.save(review);



        // Update PG rating
        List<Review> reviews = reviewRepository.findByPg(pg);

        double avg = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        pg.setAverageRating(avg);
        pg.setTotalReviews(reviews.size());
        pgRepository.save(pg);

        return review;
    }

    // ===============================
    // Public reviews for PG
    // ===============================
    @Override
    public List<Review> getReviewsForPg(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        return reviewRepository.findByPg(pg);
    }

    // ===============================
    // ADMIN deletes review
    // ===============================
    @Override
    @Transactional
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Pg pg = review.getPg();

        reviewRepository.delete(review);

        // Recalculate rating
        List<Review> reviews = reviewRepository.findByPg(pg);

        double avg = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        pg.setAverageRating(avg);
        pg.setTotalReviews(reviews.size());
        pgRepository.save(pg);
    }
}
