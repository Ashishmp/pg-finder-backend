package com.pgfinder.pg_finder_backend.dto.response.analytics;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OwnerDashboardResponse(
        PortfolioMetrics portfolio,
        BookingMetrics bookings,
        RevenueMetrics revenue,
        RatingMetrics ratings
) {}
