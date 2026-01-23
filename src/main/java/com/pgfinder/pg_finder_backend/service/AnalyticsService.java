package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.response.OwnerAnalyticsResponse;

public interface   AnalyticsService {
    OwnerAnalyticsResponse getOwnerSummary();

}
