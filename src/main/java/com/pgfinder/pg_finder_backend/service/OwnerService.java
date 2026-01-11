package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OwnerService {
    List<PgResponse> getMyPgs( Long pgId);
    PgResponse getMyPg( Long pgId, Long ownerId);
}
