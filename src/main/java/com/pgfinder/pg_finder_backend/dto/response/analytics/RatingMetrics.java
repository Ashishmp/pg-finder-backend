package com.pgfinder.pg_finder_backend.dto.response.analytics;

public record RatingMetrics(
        Double averageRating,
        long totalReviews
) {}
