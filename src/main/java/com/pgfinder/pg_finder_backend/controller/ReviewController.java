package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.response.ReviewResponse;
import com.pgfinder.pg_finder_backend.entity.Review;
import com.pgfinder.pg_finder_backend.mapper.ReviewMapper;
import com.pgfinder.pg_finder_backend.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //===========================
    // USER: Create review
    //===========================

    @PostMapping("/bookings/{bookingId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @PathVariable Long bookingId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment
    ) {
        Review review = reviewService.createReview(bookingId, rating, comment);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Review submitted successfully",
                        ReviewMapper.toResponse(review))

        );
    }

    //===========================
    // PUBLIC: Get PG reviews
    //===========================

    @GetMapping("/pgs/{pgId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviews(@PathVariable Long pgId) {

        List<Review> reviews = reviewService.getReviewsForPg(pgId);

        return ResponseEntity.ok(
                new ApiResponse<>(true,"Reviews fetched successfully",
                        ReviewMapper.toResponse(reviews))
        );
    }

    //===========================
    // ADMIN: Delete review
    //===========================

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Object>> deleteReview(@PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok(
               new ApiResponse<>(true,"Review removed successfully", null)
        );
    }
}
