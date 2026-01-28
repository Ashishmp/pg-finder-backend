package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.response.PageResponse;
import com.pgfinder.pg_finder_backend.dto.response.RoomResponse;

public interface RoomService {

    RoomResponse createRoom(Long pgId, CreateRoomRequest request);

    PageResponse<RoomResponse> getRoomsByPg(
            Long pgId,
            int page,
            int size,
            String sortBy,
            String direction,
            Integer sharingType,
            Boolean ac,
            String status,
            Integer minRent,
            Integer maxRent
    );

    RoomResponse updateRoom(Long roomId, UpdateRoomRequest request);

    void deleteRoom(Long roomId);
}
