package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.request.PgSearchRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Amenity;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.PgStatus;
import com.pgfinder.pg_finder_backend.enums.Role;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.repository.AmenityRepository;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.service.PgService;
import com.pgfinder.pg_finder_backend.specification.PgSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PgServiceImpl implements PgService {

    private final PgRepository pgRepository;
    private final UserRepository userRepository;
    private final AmenityRepository amenityRepository;

    public PgServiceImpl(PgRepository pgRepository, UserRepository userRepository, AmenityRepository amenityRepository, AmenityRepository amenityRepository1) {
        this.pgRepository = pgRepository;
        this.userRepository = userRepository;
        this.amenityRepository = amenityRepository;

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
        pg.setStatus(PgStatus.PENDING);
        pg.setCreatedAt(LocalDateTime.now());
        pg.setUpdatedAt(LocalDateTime.now());

        Pg saved = pgRepository.save(pg);
        return mapToPgResponse(saved);
    }

    // ---------------- PUBLIC ----------------

    @Override
    public List<PgPublicDetailResponse> getAllPgs() {
        return pgRepository.findByStatus(PgStatus.ACTIVE)
                .stream()
                .map(this::mapToPublic)
                .toList();
    }


    @Override
    public PgPublicDetailResponse getPublicPgById(Long pgId) {
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (pg.getStatus() != PgStatus.ACTIVE) {
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
                .orElseThrow(() -> new BusinessException("PG not found " + pgId));

        if (!pg.getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to view this PG");
        }
        return mapToPrivate(pg);
    }
    // -----------------------------------------------
    // ---------------- OWNER / ADMIN ----------------
    // -----------------------------------------------

    @Override
    public PgResponse updatePg(Long pgId, UpdatePgRequest request, Long userId) {

        Pg pg = getPgForOwner(pgId, userId);

        if (request.getPgName() != null) pg.setPgName(request.getPgName());
        if (request.getPgAddress() != null) pg.setPgAddress(request.getPgAddress());
        if (request.getDescription() != null) pg.setDescription(request.getDescription());
        if (request.getPgCity() != null) pg.setPgCity(request.getPgCity());
        if (request.getPgState() != null) pg.setPgState(request.getPgState());
        if (request.getPgCountry() != null) pg.setPgCountry(request.getPgCountry());
        if (request.getPgPostalCode() != null) pg.setPgPostalCode(request.getPgPostalCode());
        if (request.getContactNumber() != null) pg.setContactNumber(request.getContactNumber());

        pg.setUpdatedAt(LocalDateTime.now());

        return mapToPgResponse(pgRepository.save(pg));
    }


    @Override
    public void deletePg(Long pgId, Long userId) {
        Pg pg = getPgForOwnerOrAdmin(pgId, userId);
        pg.setStatus(PgStatus.INACTIVE);
        pgRepository.save(pg);
    }

//NO LONGER NEEDED AS OF NOW PG CAN BE DELETED AND ADDED BY OWNER ONLY


//    @Override
//    public PgResponse updatePgStatus(Long pgId, PgStatus status, Long userId) {
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new BusinessException("User not found"));
//
//        if (user.getRole() != Role.ADMIN) {
//            throw new AccessDeniedException("Only admin can update PG status");
//        }
//
//        Pg pg = pgRepository.findById(pgId)
//                .orElseThrow(() -> new BusinessException("PG not found"));
//
//        pg.setStatus(status);
//        return mapToPgResponse(pgRepository.save(pg));
//    }

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
        r.setStatus(pg.getStatus().name());
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
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (pg.getStatus() != PgStatus.PENDING) {
            throw new BusinessException("Only pending PGs can be approved");
        }

        pg.setStatus(PgStatus.ACTIVE);

        User owner = pg.getOwner();
        if (owner.getRole() == Role.USER) {
            owner.setRole(Role.OWNER);
            userRepository.save(owner);
        }

        return pgRepository.save(pg);
    }


    @Override
    @Transactional
    public Pg rejectPg(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        if (pg.getStatus() != PgStatus.PENDING) {
            throw new RuntimeException("Only pending PGs can be rejected");
        }

        pg.setStatus(PgStatus.REJECTED);
        return pgRepository.save(pg);
    }
    @Override
    @Transactional
    public void assignAmenities(Long pgId, List<Long> amenityIds) {

        User owner = getCurrentUser();

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You do not own this PG");
        }

        Set<Amenity> amenities =
                new HashSet<>(amenityRepository.findAllById(amenityIds));

        pg.setAmenities(amenities);
        pgRepository.save(pg);
    }

    @Override
    @Transactional
    public void updateRules(Long pgId, String rules) {

        User owner = getCurrentUser();

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You do not own this PG");
        }

        pg.setRules(rules);
        pgRepository.save(pg);
    }
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public Page<Pg> searchPgs(PgSearchRequest request) {

        int page = request.getPage() >= 0 ? request.getPage() : 0;
        int size = request.getSize() > 0 ? request.getSize() : 10;

        size = Math.min(size, 50);

        Sort.Direction direction =
                request.getDirection() != null
                        ? Sort.Direction.fromString(request.getDirection())
                        : Sort.Direction.ASC;

        String sortBy =
                request.getSortBy() != null && !request.getSortBy().isBlank()
                        ? request.getSortBy()
                        : "createdAt";

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        return pgRepository.findAll(
                PgSpecification.withFilters(request),
                pageable
        );
    }

}
