package com.pgfinder.pg_finder_backend.dto.response.analytics;

public record BookingMetrics(
        long totalBookings,
        long monthlyBookings,
        long activeBookings,
        long completedBookings
) {}
