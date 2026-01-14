package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.security.AuthUtil;
import com.pgfinder.pg_finder_backend.service.PgService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pgs")
public class PgController {

    private final PgService pgService;

    public PgController(PgService pgService) {
        this.pgService = pgService;
    }

    // ========================
    // Create PG (Logged-in USER becomes OWNER of this PG)
    // ========================

    @PostMapping
    public ResponseEntity<ApiResponse<PgResponse>> createPg(
            @Valid @RequestBody CreatePgRequest request) {

        Long userId = AuthUtil.getUserId();
        PgResponse response = pgService.createPg(request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Your PG has been created", response));

    }

    // ========================
    // Public – List all PGs (no contact numbers)
    // ========================

    @GetMapping
    public ResponseEntity<ApiResponse<List<PgPublicDetailResponse>>> getAllPgs() {
        List<PgPublicDetailResponse> pgs = pgService.getAllPgs();
        return ResponseEntity.ok(
                new ApiResponse<>(true,"List all Pgs",pgs)
        );
    }

    // ========================
    // Public – View PG details (no contact)
    // ========================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PgPublicDetailResponse>> getPublicPg(@PathVariable Long id) {

        PgPublicDetailResponse pg = pgService.getPublicPgById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Basic PG datails fetched successfully", pg)
        );
    }

    // ========================
    // Private – View PG with contact (JWT required)
    // ========================

    @GetMapping("/{id}/full")
    public ResponseEntity<ApiResponse<PgPrivateDetailResponse>> getPrivatePg(@PathVariable Long id) {
        Long userId = AuthUtil.getUserId();
        PgPrivateDetailResponse pgs = pgService.getPrivatePgById(userId, id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, " PG fetched successfully with contact details", pgs)
        );
    }

    // ========================
    // Owner-only – Update PG
    // ========================

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PgResponse>> updatePg(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePgRequest request) {

        Long userId = AuthUtil.getUserId();
        PgResponse response = pgService.updatePg(id, request, userId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Your PG has been updated successfully", response)
        );
    }

    // ========================
    // Owner or Admin – Soft delete PG
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
    // Owner or Admin – Activate / Pause PG
    // ========================

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PgResponse>> changePgStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Long userId = AuthUtil.getUserId();
        PgResponse response = pgService.updatePgStatus(id, status, userId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Your PG has been updated successfully", response)
        );
    }
}


//PG ADDRESS & LOCATION
//API	Method	Role	Functionality
///api/pgs/{pgId}/address	PUT	OWNER	Update location
///api/pgs/cities	GET	ALL	List cities
///api/pgs/areas	GET	ALL	Areas by city
