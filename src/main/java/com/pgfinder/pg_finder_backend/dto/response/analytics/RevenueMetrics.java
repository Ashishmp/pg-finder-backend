package com.pgfinder.pg_finder_backend.dto.response.analytics;

public record RevenueMetrics(
        double totalRevenue,
        double monthlyRevenue
) {}
