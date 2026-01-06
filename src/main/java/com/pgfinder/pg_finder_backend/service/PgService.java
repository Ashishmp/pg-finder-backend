package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;

import java.util.List;

public interface PgService {

PgResponse createPg(CreatePgRequest request, Long userId);
List<PgPublicDetailResponse> getAllPgs();
//PgPrivateDetailResponse getPgDetail();

PgPrivateDetailResponse getPgById(Long id);
}

