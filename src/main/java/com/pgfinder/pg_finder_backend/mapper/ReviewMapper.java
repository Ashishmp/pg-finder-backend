package com.pgfinder.pg_finder_backend.mapper;

import com.pgfinder.pg_finder_backend.dto.response.ReviewResponse;
import com.pgfinder.pg_finder_backend.entity.Review;

import java.util.List;

public class ReviewMapper {

    public static ReviewResponse toResponse(Review review) {

        ReviewResponse dto = new ReviewResponse();
        dto.setReviewId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setUserName(review.getUser().getName());
        dto.setCreatedAt(review.getCreatedAt().toString());

        return dto;
    }

    public static List<ReviewResponse> toResponse(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }
}
