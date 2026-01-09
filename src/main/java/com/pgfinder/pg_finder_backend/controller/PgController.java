package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
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

    @PostMapping("/create")
    public ResponseEntity<PgResponse> createPg(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody CreatePgRequest request) {

        PgResponse response = pgService.createPg(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/viewAll")
    public ResponseEntity<List<PgPublicDetailResponse>>viewAllPg(){
        return ResponseEntity.ok(pgService.getAllPgs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PgPrivateDetailResponse> getPgDetail(@PathVariable Long id) {
        return ResponseEntity.ok(pgService.getPgById(id));
    }


}
///api/pgs	POST	OWNER	Create PG
///api/pgs/{pgId}	GET	ALL	View PG details
///api/pgs/{pgId}	PUT	OWNER	Update PG
///api/pgs/{pgId}	DELETE	OWNER	Soft delete
///api/pgs/{pgId}/status	PATCH	OWNER/ADMIN	Activate / Pause
//📍 PG ADDRESS & LOCATION
//API	Method	Role	Functionality
///api/pgs/{pgId}/address	PUT	OWNER	Update location
///api/pgs/cities	GET	ALL	List cities
///api/pgs/areas	GET	ALL	Areas by city
