package com.pgfinder.pg_finder_backend.mapper;

import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;

import java.util.List;

public class PgMapper {

    public static PgResponse toResponse(Pg pg) {

        PgResponse dto = new PgResponse();
        dto.setPgId(pg.getId());
        dto.setPgName(pg.getPgName());
        dto.setPgCity(pg.getPgCity());
        dto.setPgState(pg.getPgState());
        dto.setDescription(pg.getDescription());
        dto.setStatus(pg.getStatus().toString());
        return dto;
    }

    public static List<PgResponse> toResponse(List<Pg> pgs) {
        return pgs.stream()
                .map(PgMapper::toResponse)
                .toList();
    }
}
