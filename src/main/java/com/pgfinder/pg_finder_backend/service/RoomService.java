package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(Long pgId, Room roomRequest);

    List<Room> getRoomsForPublicOrOwner(Long pgId);

    Room updateRoom(Long roomId, Room roomRequest);

    void disableRoom(Long roomId);
}
