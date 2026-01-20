package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.service.AmenityService;
import com.pgfinder.pg_finder_backend.service.PgService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AmenityController {

    private final AmenityService amenityService;
    private final PgService pgService;

    public AmenityController(AmenityService amenityService, PgService pgService) {
        this.amenityService = amenityService;
        this.pgService = pgService;
    }

    // ==========================
    // PUBLIC: master amenities
    // ==========================
    @GetMapping("/amenities")
    public ResponseEntity<ApiResponse<List<String>>> getAmenities() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Amenities fetched successfully",
                        amenityService.getAllAmenityNames()
                )
        );
    }

    // ==========================
    // OWNER: assign amenities
    // ==========================
    @PutMapping("/pgs/{pgId}/amenities")
    public ResponseEntity<ApiResponse<Object>> assignAmenities(
            @PathVariable Long pgId,
            @RequestBody List<Long> amenityIds) {

        pgService.assignAmenities(pgId, amenityIds);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Amenities updated successfully", null)
        );
    }

    // ==========================
    // OWNER: set rules
    // ==========================
    @PutMapping("/pgs/{pgId}/rules")
    public ResponseEntity<ApiResponse<Object>> setRules(
            @PathVariable Long pgId,
            @RequestBody String rules) {

        pgService.updateRules(pgId, rules);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Rules updated successfully", null)
        );
    }
}
