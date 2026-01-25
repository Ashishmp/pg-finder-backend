package com.pgfinder.pg_finder_backend.mapper;

import com.pgfinder.pg_finder_backend.dto.response.PgMiniResponse;
import com.pgfinder.pg_finder_backend.dto.response.RoomResponse;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.Room;

import java.util.List;

public class RoomMapper {

    public static RoomResponse toResponse(Room room) {

        RoomResponse r = new RoomResponse();
        r.setRoomId(room.getId());
        r.setRent(room.getRent());
        r.setSharingType(room.getSharingType());
        r.setAc(room.isAc());
        r.setTotalBeds(room.getTotalBeds());
        r.setAvailableBeds(room.getAvailableBeds());
        r.setStatus(room.getStatus().name());

        Pg pg = room.getPg();
        PgMiniResponse pgDto = new PgMiniResponse();
        pgDto.setPgId(pg.getId());
        pgDto.setPgName(pg.getPgName());
        pgDto.setPgCity(pg.getPgCity());
        pgDto.setPgState(pg.getPgState());

        r.setPg(pgDto);

        return r;
    }
    public static List<RoomResponse> toResponse(List<Room> rooms) {
        return rooms.stream()
                .map(RoomMapper::toResponse)
                .toList();
    }
}
