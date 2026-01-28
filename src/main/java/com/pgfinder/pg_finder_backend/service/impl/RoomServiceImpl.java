package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.request.CreateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.response.PageResponse;
import com.pgfinder.pg_finder_backend.dto.response.RoomResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.enums.RoomStatus;
import com.pgfinder.pg_finder_backend.exception.BusinessException;
import com.pgfinder.pg_finder_backend.mapper.RoomMapper;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.repository.RoomRepository;
import com.pgfinder.pg_finder_backend.service.RoomService;
import com.pgfinder.pg_finder_backend.specification.RoomSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PgRepository pgRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           PgRepository pgRepository) {
        this.roomRepository = roomRepository;
        this.pgRepository = pgRepository;
    }

    @Override
    public RoomResponse createRoom(Long pgId, CreateRoomRequest request) {

        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new BusinessException("PG not found"));

        if (request.getTotalBeds() <= 0) {
            throw new BusinessException("Total beds must be greater than 0");
        }

        boolean exists = roomRepository.existsByPgIdAndSharingTypeAndAc(
                pgId,
                request.getSharingType(),
                request.getAc()
        );

        if (exists) {
            throw new BusinessException("Duplicate room configuration for PG");
        }

        Room room = new Room();
        room.setPg(pg);
        room.setSharingType(request.getSharingType());
        room.setRent(Double.valueOf(request.getRent()));
        room.setTotalBeds(request.getTotalBeds());
        room.setAvailableBeds(request.getTotalBeds());
        room.setAc(request.getAc());
        room.setStatus(RoomStatus.ACTIVE);

        return RoomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RoomResponse> getRoomsByPg(
            Long pgId,
            int page,
            int size,
            String sortBy,
            String direction,
            Integer sharingType,
            Boolean ac,
            String status,
            Integer minRent,
            Integer maxRent) {

        if (!pgRepository.existsById(pgId)) {
            throw new BusinessException("PG not found");
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Room> spec = Specification
                .where(RoomSpecification.byPg(pgId))
                .and(RoomSpecification.hasSharingType(sharingType))
                .and(RoomSpecification.hasAc(ac))
                .and(RoomSpecification.hasStatus(status))
                .and(RoomSpecification.rentBetween(minRent, maxRent));

        Page<Room> roomPage = roomRepository.findAll(spec, pageable);

        Page<RoomResponse> mappedPage =
                roomPage.map(RoomMapper::toResponse);

        return PageResponse.from(mappedPage);

    }

    @Override
    public RoomResponse updateRoom(Long roomId, UpdateRoomRequest request) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException("Room not found"));

        int occupiedBeds = room.getTotalBeds() - room.getAvailableBeds();

        if (request.getTotalBeds() <= 0) {
            throw new BusinessException("Total beds must be greater than 0");
        }

        if (request.getTotalBeds() < occupiedBeds) {
            throw new BusinessException("Total beds cannot be less than occupied beds");
        }

        room.setRent(Double.valueOf(request.getRent()));
        room.setTotalBeds(request.getTotalBeds());
        room.setAvailableBeds(request.getTotalBeds() - occupiedBeds);

        return RoomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException("Room not found"));

        room.setStatus(RoomStatus.INACTIVE);
        roomRepository.save(room);
    }
}
