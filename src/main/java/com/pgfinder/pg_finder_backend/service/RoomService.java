package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.dto.request.CreateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateRoomRequest;
import com.pgfinder.pg_finder_backend.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(Long pgId, CreateRoomRequest request);

    List<Room> getRoomsForPublicOrOwner(Long pgId);

    Room updateRoom(Long roomId, UpdateRoomRequest roomRequest);

    void disableRoom(Long roomId);
}
