package com.pgfinder.pg_finder_backend.controller.analytics;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.response.analytics.OwnerDashboardResponse;
import com.pgfinder.pg_finder_backend.service.analytics.OwnerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner/dashboard")
@RequiredArgsConstructor
public class OwnerDashboardController {

    private final OwnerDashboardService dashboardService;

    @GetMapping("/summary")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<OwnerDashboardResponse>> getDashboardSummary() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Owner dashboard fetched successfully",
                        dashboardService.getDashboard()
                )
        );
    }
}
