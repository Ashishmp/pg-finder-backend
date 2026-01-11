package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.security.AuthUtil;
import com.pgfinder.pg_finder_backend.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/v1/owners/me")
public class OwnerController {
    private  final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {this.ownerService = ownerService;}

    @GetMapping("/pgs")
    public ResponseEntity<ApiResponse<List<PgResponse>>> getMyPgs(){
        Long userId = AuthUtil.getUserId();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Your PG fetched", ownerService.getMyPgs(userId))
        );
    }

    @GetMapping("/pgs/{pgId}")
    public ResponseEntity<ApiResponse<PgResponse>> getMyPg(@PathVariable Long pgId){
        Long userId = AuthUtil.getUserId();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Your PG fetched", ownerService.getMyPg(pgId, userId))
        );
    }
}
