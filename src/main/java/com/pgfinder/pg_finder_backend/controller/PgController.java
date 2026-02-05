package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.request.PgSearchRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PageResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.enums.PgStatus;
import com.pgfinder.pg_finder_backend.mapper.PgMapper;
import com.pgfinder.pg_finder_backend.security.AuthUtil;
import com.pgfinder.pg_finder_backend.service.PgService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pgs")
public class PgController {

    private final PgService pgService;

    public PgController(PgService pgService) {
        this.pgService = pgService;
    }

    // ========================
    // Create PG
    // ========================

    @PostMapping
    public ResponseEntity<ApiResponse<PgResponse>> createPg(
            @Valid @RequestBody CreatePgRequest request) {

        Long userId = AuthUtil.getUserId();
        PgResponse response = pgService.createPg(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Your PG has been created", response));
    }

    // ========================
    // Public – List all PGs
    // ========================

    @GetMapping
    public ResponseEntity<ApiResponse<List<PgPublicDetailResponse>>> getAllPgs() {

        List<PgPublicDetailResponse> pgs =

                Arrays.asList(pgService.getAllPgs());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "PGs fetched successfully",
                        pgs
                )
        );
    }


    // ========================
    // Public – View PG
    // ========================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PgPublicDetailResponse>> getPublicPg(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Basic PG details fetched successfully",
                        pgService.getPublicPgById(id))
        );
    }

    // ========================
    // Private – View PG with contact
    // ========================

    @GetMapping("/{id}/full")
    public ResponseEntity<ApiResponse<PgPrivateDetailResponse>> getPrivatePg(
            @PathVariable Long id) {

        Long userId = AuthUtil.getUserId();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "PG fetched successfully with contact details",
                        pgService.getPrivatePgById(id, userId))
        );
    }

    // ========================
    // Owner – Update PG
    // ========================

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PgResponse>> updatePg(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePgRequest request) {

        Long userId = AuthUtil.getUserId();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Your PG has been updated successfully",
                        pgService.updatePg(id, request, userId))
        );
    }

    // ========================
    // Owner/Admin – Delete PG
    // ========================

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePg(@PathVariable Long id) {

        Long userId = AuthUtil.getUserId();
        pgService.deletePg(id, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "PG deleted successfully", null)
        );
    }

    // ========================
    // Search PGs
    // ========================

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<PgResponse>>> searchPgs(
            @ModelAttribute PgSearchRequest request) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "PGs fetched successfully",
                        pgService.searchPgs(request))
        );
    }
}
