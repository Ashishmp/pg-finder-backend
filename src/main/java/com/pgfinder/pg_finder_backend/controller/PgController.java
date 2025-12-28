package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.service.PgService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pgs")
public class PgController {

    private final PgService pgService;

    public PgController(PgService pgService) {
        this.pgService = pgService;
    }

    @PostMapping("/create")
    public ResponseEntity<PgResponse> createPg(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody CreatePgRequest request) {

        PgResponse response = pgService.createPg(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
