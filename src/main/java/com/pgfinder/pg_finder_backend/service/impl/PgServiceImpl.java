package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.PgStatus;
import com.pgfinder.pg_finder_backend.enums.Role;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.service.PgService;
import jakarta.transaction.Transactional;
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
    // ----------------------------------------
    // ---------------- CREATE ----------------
    // ----------------------------------------

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
        pg.setContactNumber(request.getContactNumber());
        pg.setOwner(owner);
        pg.setStatus(PgStatus.PENDING.name());
        pg.setCreatedAt(LocalDateTime.now());
        pg.setUpdatedAt(LocalDateTime.now());

        Pg saved = pgRepository.save(pg);
        return mapToPgResponse(saved);
    }

    // ---------------- PUBLIC ----------------

    @Override
    public List<PgPublicDetailResponse> getAllPgs() {
        return pgRepository.findAll().stream()
                .filter(pg -> "ACTIVE".equals(pg.getStatus()))
                .map(this::mapToPublic)
                .toList();
    }

    @Override
    public PgPublicDetailResponse getPublicPgById(Long pgId) {
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (!"ACTIVE".equals(pg.getStatus())) {
            throw new BusinessException("PG not available");
        }

        return mapToPublic(pg);
    }
    // -----------------------------------------
    // ---------------- PRIVATE ----------------
    // -----------------------------------------

    @Override
    public PgPrivateDetailResponse getPrivatePgById(Long pgId, Long userId) {
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        return mapToPrivate(pg);
    }
    // -----------------------------------------------
    // ---------------- OWNER / ADMIN ----------------
    // -----------------------------------------------

    @Override
    public PgResponse updatePg(Long pgId, UpdatePgRequest request, Long userId) {
        Pg pg = getPgForOwner(pgId, userId);

        pg.setPgName(request.getPgName());
        pg.setPgAddress(request.getPgAddress());
        pg.setDescription(request.getDescription());
        pg.setPgCity(request.getPgCity());
        pg.setPgState(request.getPgState());
        pg.setPgCountry(request.getPgCountry());
        pg.setPgPostalCode(request.getPgPostalCode());
        pg.setContactNumber(request.getContactNumber());
        pg.setUpdatedAt(LocalDateTime.now());

        return mapToPgResponse(pgRepository.save(pg));
    }

    @Override
    public void deletePg(Long pgId, Long userId) {
        Pg pg = getPgForOwnerOrAdmin(pgId, userId);
        pg.setStatus("DELETED");
        pgRepository.save(pg);
    }

    @Override
    public PgResponse updatePgStatus(Long pgId, String status, Long userId) {
        Pg pg = getPgForOwnerOrAdmin(pgId, userId);
        pg.setStatus(status.toUpperCase());
        return mapToPgResponse(pgRepository.save(pg));
    }
    // ------------------------------------------
    // ---------------- SECURITY ----------------
    // ------------------------------------------

    private Pg getPgForOwner(Long pgId, Long userId) {
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (!pg.getOwner().getId().equals(userId)) {
            throw new BusinessException("You do not own this PG");
        }
        return pg;
    }

    private Pg getPgForOwnerOrAdmin(Long pgId, Long userId) {
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        if (!pg.getOwner().getId().equals(userId) && user.getRole() != Role.ADMIN) {
            throw new BusinessException("Unauthorized");
        }
        return pg;
    }
    // ---------------------------------------------
    // ---------------- DTO MAPPERS ----------------
    // ---------------------------------------------

    private PgResponse mapToPgResponse(Pg pg) {
        PgResponse r = new PgResponse();
        r.setPgId(pg.getId());
        r.setPgName(pg.getPgName());
        r.setPgCity(pg.getPgCity());
        r.setPgState(pg.getPgState());
        r.setStatus(pg.getStatus());
        return r;
    }

    private PgPublicDetailResponse mapToPublic(Pg pg) {
        PgPublicDetailResponse r = new PgPublicDetailResponse();
        r.setPgId(pg.getId());
        r.setPgName(pg.getPgName());
        r.setPgCity(pg.getPgCity());
        r.setPgState(pg.getPgState());
        r.setDescription(pg.getDescription());
        return r;
    }

    private PgPrivateDetailResponse mapToPrivate(Pg pg) {
        PgPrivateDetailResponse r = new PgPrivateDetailResponse();
        r.setPgId(pg.getId());
        r.setPgName(pg.getPgName());
        r.setPgAddress(pg.getPgAddress());
        r.setPgCity(pg.getPgCity());
        r.setPgState(pg.getPgState());
        r.setPgCountry(pg.getPgCountry());
        r.setPgPostalCode(pg.getPgPostalCode());
        r.setDescription(pg.getDescription());
        r.setContactNumber(pg.getContactNumber());
        return r;
    }
    @Override
    public List<Pg> getPendingPgs() {
        return pgRepository.findByStatus(PgStatus.PENDING);
    }

    @Override
    @Transactional
    public Pg approvePg(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        if (!pg.getStatus().equals(PgStatus.PENDING.name())) {
            throw new RuntimeException("Only pending PGs can be approved");
        }

        pg.setStatus(PgStatus.ACTIVE.name());
        return pgRepository.save(pg);
    }


    @Override
    @Transactional
    public Pg rejectPg(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        if (pg.getStatus() != PgStatus.PENDING.name()) {
            throw new RuntimeException("Only pending PGs can be rejected");
        }

        pg.setStatus(PgStatus.REJECTED.name());
        return pgRepository.save(pg);
    }

}
