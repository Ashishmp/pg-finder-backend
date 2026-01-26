package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.CreatePgRequest;
import com.pgfinder.pg_finder_backend.dto.request.PgSearchRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdatePgRequest;
import com.pgfinder.pg_finder_backend.dto.response.PageResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPrivateDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgPublicDetailResponse;
import com.pgfinder.pg_finder_backend.dto.response.PgResponse;
import com.pgfinder.pg_finder_backend.entity.Amenity;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.PgStatus;
import com.pgfinder.pg_finder_backend.enums.Role;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.mapper.PgMapper;
import com.pgfinder.pg_finder_backend.repository.AmenityRepository;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.service.PgService;
import com.pgfinder.pg_finder_backend.specification.PgSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PgServiceImpl implements PgService {

    private final PgRepository pgRepository;
    private final UserRepository userRepository;
    private final AmenityRepository amenityRepository;

    // ---------------- CREATE ----------------

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

        return PgMapper.toResponse(pgRepository.save(pg));
    }

    // ---------------- PUBLIC ----------------

    @Override
    public List<PgPublicDetailResponse> getAllPgs() {
        return pgRepository.findByStatus(PgStatus.ACTIVE)
                .stream()
                .map(PgMapper::toPublic)
                .toList();
    }

    @Override
    public PgPublicDetailResponse getPublicPgById(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (pg.getStatus() != PgStatus.ACTIVE) {
            throw new BusinessException("PG not available");
        }

        return PgMapper.toPublic(pg);
    }

    // ---------------- PRIVATE ----------------

    @Override
    public PgPrivateDetailResponse getPrivatePgById(Long pgId, Long userId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (!pg.getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to view this PG");
        }

        return PgMapper.toPrivate(pg);
    }

    // ---------------- OWNER / ADMIN ----------------

    @Override
    public PgResponse updatePg(Long pgId, UpdatePgRequest request, Long userId) {

        Pg pg = getPgForOwner(pgId, userId);

        PgMapper.updatePgFromRequest(pg, request);
        pg.setUpdatedAt(LocalDateTime.now());

        return PgMapper.toResponse(pgRepository.save(pg));
    }

    @Override
    public void deletePg(Long pgId, Long userId) {

        Pg pg = getPgForOwnerOrAdmin(pgId, userId);
        pg.setStatus(PgStatus.INACTIVE);
        pgRepository.save(pg);
    }

    @Override
    public List<PgResponse> getPendingPgs() {
        return pgRepository.findByStatus(PgStatus.PENDING)
                .stream()
                .map(PgMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PgResponse approvePg(Long pgId) {

        Pg pg = getPendingPg(pgId);

        pg.setStatus(PgStatus.ACTIVE);

        User owner = pg.getOwner();
        if (owner.getRole() == Role.USER) {
            owner.setRole(Role.OWNER);
            userRepository.save(owner);
        }

        return PgMapper.toResponse(pgRepository.save(pg));
    }

    @Override
    @Transactional
    public PgResponse rejectPg(Long pgId) {

        Pg pg = getPendingPg(pgId);
        pg.setStatus(PgStatus.REJECTED);

        return PgMapper.toResponse(pgRepository.save(pg));
    }

    // ---------------- AMENITIES / RULES ----------------

    @Override
    @Transactional
    public void assignAmenities(Long pgId, List<Long> amenityIds) {

        Pg pg = getPgForOwner(pgId, getCurrentUser().getId());

        Set<Amenity> amenities =
                new HashSet<>(amenityRepository.findAllById(amenityIds));

        pg.setAmenities(amenities);
        pgRepository.save(pg);
    }

    @Override
    @Transactional
    public void updateRules(Long pgId, String rules) {

        Pg pg = getPgForOwner(pgId, getCurrentUser().getId());
        pg.setRules(rules);
        pgRepository.save(pg);
    }

    // ---------------- SEARCH ----------------

    @Override
    public PageResponse<PgResponse> searchPgs(PgSearchRequest request) {

        int page = Math.max(request.getPage(), 0);
        int size = Math.min(Math.max(request.getSize(), 10), 50);

        Sort.Direction direction =
                request.getDirection() != null
                        ? Sort.Direction.fromString(request.getDirection())
                        : Sort.Direction.ASC;

        Set<String> allowedSortFields = Set.of("createdAt", "pgName", "pgCity");
        String sortBy = allowedSortFields.contains(request.getSortBy())
                ? request.getSortBy()
                : "createdAt";

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Pg> pgPage = pgRepository.findAll(
                PgSpecification.withFilters(request),
                pageable
        );

        return PageResponse.from(pgPage.map(PgMapper::toResponse));
    }

    // ---------------- HELPERS ----------------

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

    private Pg getPendingPg(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (pg.getStatus() != PgStatus.PENDING) {
            throw new BusinessException("Only pending PGs allowed");
        }
        return pg;
    }

    private User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Unauthorized");
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new BusinessException("User not found"));
    }
}
