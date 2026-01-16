package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.entity.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(Long bookingId, Integer rating, String comment);

    List<Review> getReviewsForPg(Long pgId);

    void deleteReview(Long reviewId); // Admin
}
