package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;

import java.util.List;

public interface PgService {

    PgResponse createPg(CreatePgRequest request, Long userId);

    List<PgPublicDetailResponse> getAllPgs();

    PgPublicDetailResponse getPublicPgById(Long pgId);

    PgPrivateDetailResponse getPrivatePgById(Long pgId, Long userId);

    PgResponse updatePg(Long pgId, UpdatePgRequest request, Long userId);

    void deletePg(Long pgId, Long userId);

    PgResponse updatePgStatus(Long pgId, String status, Long userId);
}
