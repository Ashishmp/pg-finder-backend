package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.response.OwnerAnalyticsResponse;
import com.pgfinder.pg_finder_backend.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/api/v1/analytics")
    public class AnalyticsController {

        private final AnalyticsService analyticsService;

        public AnalyticsController(AnalyticsService analyticsService) {
            this.analyticsService = analyticsService;
        }

        @GetMapping("/owner/summary")
        public ResponseEntity<ApiResponse<OwnerAnalyticsResponse>> ownerSummary() {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Owner analytics fetched successfully",
                            analyticsService.getOwnerSummary()
                    )
            );
        }
    }


