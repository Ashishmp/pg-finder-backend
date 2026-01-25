package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.CreateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateRoomRequest;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.RoomStatus;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.repository.RoomRepository;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.service.RoomService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PgRepository pgRepository;
    private final UserRepository userRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           PgRepository pgRepository,
                           UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.pgRepository = pgRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUserOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return null;
        }
        return userRepository.findByEmail(auth.getName()).orElse(null);
    }

    // ============================
    // CREATE ROOM
    // ============================

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public Room createRoom(Long pgId, CreateRoomRequest request) {

        User owner = getCurrentUser();
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        // Ownership check
        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You do not own this PG");
        }

        // Prevent duplicate room type (pg + sharing + ac)
        roomRepository.findByPgAndSharingTypeAndIsAc(
                pg,
                request.getSharingType(),
                request.getAc()
        ).ifPresent(r -> {
            throw new RuntimeException("This room type already exists for this PG");
        });


        if (request.getTotalBeds() <= 0) {
            throw new RuntimeException("Total beds must be greater than 0");
        }

        Room room = new Room();
        room.setPg(pg);
        room.setSharingType(request.getSharingType());
        room.setRent(Double.valueOf(request.getRent()));
        room.setTotalBeds(request.getTotalBeds());
        room.setAvailableBeds(request.getTotalBeds()); // derived
        room.setAc(request.getAc());
        room.setStatus(RoomStatus.ACTIVE);

        return roomRepository.save(room);
    }

    // ============================
    // LIST ROOMS (PUBLIC OR OWNER)
    // ============================
    @Override
    public List<Room> getRoomsForPublicOrOwner(Long pgId) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        User user = getCurrentUserOrNull();

        // If caller is owner → return all rooms
        if (user != null && pg.getOwner().getId().equals(user.getId())) {
            return roomRepository.findByPg(pg);
        }

        // Public → only active & available
        return roomRepository.findByPgAndStatusAndAvailableBedsGreaterThan(
                pg,
                RoomStatus.ACTIVE,
                0
        );
    }

    // ============================
    // UPDATE ROOM
    // ============================
    @Override
    @Transactional
    public Room updateRoom(Long roomId, UpdateRoomRequest request) {

        User owner = getCurrentUser();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException("Room not found"));

        Pg pg = room.getPg();
        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new BusinessException("You do not own this PG");
        }

        // Prevent duplicate room type (sharing + AC)
        roomRepository.findByPgAndSharingTypeAndIsAc(
                pg,
                request.getSharingType(),
                request.getAc()
        ).ifPresent(existing -> {
            if (!existing.getId().equals(roomId)) {
                throw new BusinessException(
                        "Room with same sharing type and AC already exists"
                );
            }
        });

        // Validation
        if (request.getTotalBeds() <= 0) {
            throw new BusinessException("Total beds must be greater than 0");
        }

        int occupiedBeds = room.getTotalBeds() - room.getAvailableBeds();

        if (request.getTotalBeds() < occupiedBeds) {
            throw new BusinessException(
                    "Total beds cannot be less than already occupied beds"
            );
        }

        int newAvailableBeds = request.getTotalBeds() - occupiedBeds;

        // Update fields
        room.setSharingType(request.getSharingType());
        room.setAc(request.getAc());
        room.setRent(Double.valueOf(request.getRent()));
        room.setTotalBeds(request.getTotalBeds());
        room.setAvailableBeds(newAvailableBeds);

        return roomRepository.save(room);
    }


    // ============================
    // DISABLE ROOM (SOFT DELETE)
    // ============================
    @Override
    @Transactional
    public void disableRoom(Long roomId) {

        User owner = getCurrentUserOrNull();
        if (owner == null) {
            throw new RuntimeException("Authentication required");
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Pg pg = room.getPg();
        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You do not own this PG");
        }

        room.setStatus(RoomStatus.INACTIVE);
        roomRepository.save(room);
    }
}
