package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.service.OwnerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Service
public class OwnerServiceImpl implements OwnerService {
    private final PgRepository pgRepository;
    public  OwnerServiceImpl(PgRepository pgRepository) {
        this.pgRepository = pgRepository;
    }
    @Override
    public List<PgResponse> getMyPgs(Long ownerId) {
        return pgRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public PgResponse getMyPg(Long pgId, Long ownerId) {
        Pg pg = pgRepository.findByIdAndOwnerId(pgId, ownerId)
                .orElseThrow(() -> new BusinessException("You do not own this pg."));
        return map(pg);
    }

    private PgResponse map(Pg pg) {
        PgResponse r = new PgResponse();
        r.setPgId(pg.getId());
        r.setPgName(pg.getPgName());
        r.setPgCity(pg.getPgCity());
        r.setPgState(pg.getPgState());
        r.setStatus(pg.getStatus());
        r.setContactNumber(pg.getContactNumber());
        r.setDescription(pg.getDescription());
        r.setPgAddress(pg.getPgAddress());
        r.setPgCountry(pg.getPgCountry());
        r.setPgPostalCode(pg.getPgPostalCode());

        return r;
    }
}
