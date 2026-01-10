package com.pgfinder.pg_finder_backend.controller;

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

    // Create PG (Logged-in USER becomes OWNER of this PG)
    @PostMapping
    public ResponseEntity<PgResponse> createPg(
            @Valid @RequestBody CreatePgRequest request) {

        Long userId = AuthUtil.getUserId();
        PgResponse response = pgService.createPg(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Public – List all PGs (no contact numbers)
    @GetMapping
    public ResponseEntity<List<PgPublicDetailResponse>> getAllPgs() {
        return ResponseEntity.ok(pgService.getAllPgs());
    }

    // Public – View PG details (no contact)
    @GetMapping("/{id}")
    public ResponseEntity<PgPublicDetailResponse> getPublicPg(@PathVariable Long id) {
        return ResponseEntity.ok(pgService.getPublicPgById(id));
    }

    // Private – View PG with contact (JWT required)
    @GetMapping("/{id}/full")
    public ResponseEntity<PgPrivateDetailResponse> getPrivatePg(@PathVariable Long id) {
        Long userId = AuthUtil.getUserId();
        return ResponseEntity.ok(pgService.getPrivatePgById(id, userId));
    }

    // Owner-only – Update PG
    @PutMapping("/{id}")
    public ResponseEntity<PgResponse> updatePg(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePgRequest request) {

        Long userId = AuthUtil.getUserId();
        return ResponseEntity.ok(pgService.updatePg(id, request, userId));
    }

    // Owner or Admin – Soft delete PG
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePg(@PathVariable Long id) {
        Long userId = AuthUtil.getUserId();
        pgService.deletePg(id, userId);
        return ResponseEntity.noContent().build();
    }

    // Owner or Admin – Activate / Pause PG
    @PatchMapping("/{id}/status")
    public ResponseEntity<PgResponse> changePgStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Long userId = AuthUtil.getUserId();
        return ResponseEntity.ok(pgService.updatePgStatus(id, status, userId));
    }
}


//PG ADDRESS & LOCATION
//API	Method	Role	Functionality
///api/pgs/{pgId}/address	PUT	OWNER	Update location
///api/pgs/cities	GET	ALL	List cities
///api/pgs/areas	GET	ALL	Areas by city
