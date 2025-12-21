package com.pgfinder.pg_finder_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/public/health-check")
    public String Health(){
        return "PG Finder Backend is running";
    }
}
