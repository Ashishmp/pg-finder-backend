package com.pgfinder.pg_finder_backend.mapper;

import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
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
        dto.setStatus(pg.getStatus().name());
        return dto;
    }

    public static PgPublicDetailResponse toPublic(Pg pg) {
        PgPublicDetailResponse dto = new PgPublicDetailResponse();
        dto.setPgId(pg.getId());
        dto.setPgName(pg.getPgName());
        dto.setPgCity(pg.getPgCity());
        dto.setPgState(pg.getPgState());
        dto.setDescription(pg.getDescription());
        return dto;
    }

    public static PgPrivateDetailResponse toPrivate(Pg pg) {
        PgPrivateDetailResponse dto = new PgPrivateDetailResponse();
        dto.setPgId(pg.getId());
        dto.setPgName(pg.getPgName());
        dto.setPgAddress(pg.getPgAddress());
        dto.setPgCity(pg.getPgCity());
        dto.setPgState(pg.getPgState());
        dto.setPgCountry(pg.getPgCountry());
        dto.setPgPostalCode(pg.getPgPostalCode());
        dto.setContactNumber(pg.getContactNumber());
        dto.setDescription(pg.getDescription());
        return dto;
    }

    public static void updatePgFromRequest(Pg pg, UpdatePgRequest request) {
        if (request.getPgName() != null) pg.setPgName(request.getPgName());
        if (request.getPgAddress() != null) pg.setPgAddress(request.getPgAddress());
        if (request.getDescription() != null) pg.setDescription(request.getDescription());
        if (request.getPgCity() != null) pg.setPgCity(request.getPgCity());
        if (request.getPgState() != null) pg.setPgState(request.getPgState());
        if (request.getPgCountry() != null) pg.setPgCountry(request.getPgCountry());
        if (request.getPgPostalCode() != null) pg.setPgPostalCode(request.getPgPostalCode());
        if (request.getContactNumber() != null) pg.setContactNumber(request.getContactNumber());
    }

    public static List<PgResponse> toResponse(List<Pg> pgs) {
        return pgs.stream().map(PgMapper::toResponse).toList();
    }
}
