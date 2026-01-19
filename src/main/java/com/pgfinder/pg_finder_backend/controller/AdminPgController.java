package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.mapper.PgMapper;
import com.pgfinder.pg_finder_backend.service.PgService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/pgs")
public class AdminPgController {

    private final PgService pgService;

    public AdminPgController(PgService pgService) {
        this.pgService = pgService;
    }

    // ==============================
    // ADMIN: View pending PGs
    // ==============================
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<PgResponse>>> pendingPgs() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Pending PGs fetched",
                        PgMapper.toResponse(pgService.getPendingPgs())
                )
        );
    }

    // ==============================
    // ADMIN: Approve PG
    // ==============================
    @PutMapping("/{pgId}/approve")
    public ResponseEntity<ApiResponse<PgResponse>> approve(@PathVariable Long pgId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "PG approved successfully",
                        PgMapper.toResponse(pgService.approvePg(pgId))
                )
        );
    }

    // ==============================
    // ADMIN: Reject PG
    // ==============================
    @PutMapping("/{pgId}/reject")
    public ResponseEntity<ApiResponse<PgResponse>> reject(@PathVariable Long pgId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "PG rejected successfully",
                        PgMapper.toResponse(pgService.rejectPg(pgId))
                )
        );
    }
}
