package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;

public interface PgService {

PgResponse createPg(CreatePgRequest request, Long userId);
}

