package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.enums.RoomStatus;
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
    @Override
    @Transactional
    public Room createRoom(Long pgId, Room roomRequest) {

        User owner = getCurrentUserOrNull();
        if (owner == null) {
            throw new RuntimeException("Authentication required");
        }

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new RuntimeException("PG not found"));

        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You do not own this PG");
        }

        // Duplicate room type check
        roomRepository.findByPgAndSharingTypeAndIsAc(
                pg,
                roomRequest.getSharingType(),
                roomRequest.getAc()
        ).ifPresent(r -> {
            throw new RuntimeException("This room type already exists for this PG");
        });

        // Validation
        if (roomRequest.getTotalBeds() <= 0) {
            throw new RuntimeException("Total beds must be > 0");
        }
        if (roomRequest.getAvailableBeds() > roomRequest.getTotalBeds()) {
            throw new RuntimeException("Available beds cannot exceed total beds");
        }

        roomRequest.setPg(pg);
        roomRequest.setStatus(RoomStatus.ACTIVE);

        return roomRepository.save(roomRequest);
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
    public Room updateRoom(Long roomId, Room updatedRoom) {

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

        // Prevent duplicate type if changed
        roomRepository.findByPgAndSharingTypeAndIsAc(
                pg,
                updatedRoom.getSharingType(),
                updatedRoom.getAc()
        ).ifPresent(existing -> {
            if (!existing.getId().equals(roomId)) {
                throw new RuntimeException("Another room with same sharing & AC exists");
            }
        });

        if (updatedRoom.getTotalBeds() <= 0) {
            throw new RuntimeException("Total beds must be > 0");
        }
        if (updatedRoom.getAvailableBeds() > updatedRoom.getTotalBeds()) {
            throw new RuntimeException("Available beds cannot exceed total beds");
        }

        room.setSharingType(updatedRoom.getSharingType());
        room.setAc(updatedRoom.getAc());
        room.setRent(updatedRoom.getRent());
        room.setTotalBeds(updatedRoom.getTotalBeds());
        room.setAvailableBeds(updatedRoom.getAvailableBeds());

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
