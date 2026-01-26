package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.request.PgSearchRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PageResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.enums.PgStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PgService {

    PgResponse createPg(CreatePgRequest request, Long userId);

    List<PgPublicDetailResponse> getAllPgs();

    PgPublicDetailResponse getPublicPgById(Long pgId);

    PgPrivateDetailResponse getPrivatePgById(Long pgId, Long userId);

    PgResponse updatePg(Long pgId, UpdatePgRequest request, Long userId);

    void deletePg(Long pgId, Long userId);

    List<PgResponse> getPendingPgs();

    PgResponse approvePg(Long pgId);

    PgResponse rejectPg(Long pgId);

    void assignAmenities(Long pgId, List<Long> amenityIds);

    void updateRules(Long pgId, String rules);

    PageResponse<PgResponse> searchPgs(PgSearchRequest request);
}
