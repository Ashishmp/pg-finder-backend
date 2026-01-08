package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.service.PgService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PgServiceImpl implements PgService {

    private final PgRepository pgRepository;
    private final UserRepository userRepository;

    public PgServiceImpl(PgRepository pgRepository, UserRepository userRepository) {
        this.pgRepository = pgRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PgResponse createPg(CreatePgRequest request, Long userId) {

        if (pgRepository.existsByPgNameAndPgAddress(
                request.getPgName(), request.getPgAddress())) {
            throw new BusinessException("PG already exists at this address");
        }

        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        Pg pg = new Pg();
        pg.setPgName(request.getPgName());
        pg.setPgAddress(request.getPgAddress());
        pg.setDescription(request.getDescription());
        pg.setPgCity(request.getPgCity());
        pg.setPgState(request.getPgState());
        pg.setPgCountry(request.getPgCountry());
        pg.setPgPostalCode(request.getPgPostalCode());
        pg.setOwner(owner);
        pg.setContactNumber(request.getContactNumber());
        pg.setCreatedAt(LocalDateTime.now());
        pg.setUpdatedAt(LocalDateTime.now());

        Pg savedPg = pgRepository.save(pg);

        PgResponse response = new PgResponse();
        response.setPgId(savedPg.getId());
        response.setPgName(savedPg.getPgName());
        response.setPgAddress(savedPg.getPgAddress());
        response.setDescription(savedPg.getDescription());
        response.setPgCity(savedPg.getPgCity());
        response.setPgState(savedPg.getPgState());
        response.setPgCountry(savedPg.getPgCountry());
        response.setPgPostalCode(savedPg.getPgPostalCode());
        response.setContactNumber(savedPg.getContactNumber());

        return response;
    }
    @Override
    public List<PgPublicDetailResponse> getAllPgs() {
        return pgRepository.findAll()
                .stream()
                .map(pg -> {
                    PgPublicDetailResponse response = new PgPublicDetailResponse();
                    response.setPgName(pg.getPgName());
                    response.setPgCity(pg.getPgCity());
                    response.setPgState(pg.getPgState());
                    response.setDescription(pg.getDescription());
                    return response;
                })
                .toList();
    }


    @Override
    public PgPrivateDetailResponse getPgById(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() ->
                        new BusinessException("PG not found with id: " + pgId)
                );

        PgPrivateDetailResponse response = new PgPrivateDetailResponse();
        response.setPgId(pg.getId());
        response.setPgName(pg.getPgName());
        response.setPgAddress(pg.getPgAddress());
        response.setPgCity(pg.getPgCity());
        response.setPgState(pg.getPgState());
        response.setPgCountry(pg.getPgCountry());
        response.setPgPostalCode(pg.getPgPostalCode());
        response.setDescription(pg.getDescription());

        // contactNumber logic (later with JWT)
        // response.setContactNumber(pg.getContactNumber());

        return response;
    }



}
