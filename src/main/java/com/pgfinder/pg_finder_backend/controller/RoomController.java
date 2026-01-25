package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.request.CreateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.response.RoomResponse;
import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.mapper.RoomMapper;
import com.pgfinder.pg_finder_backend.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // ========================
    // OWNER: Add room
    // ========================

    @PostMapping("/pgs/{pgId}/rooms")
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(
            @PathVariable Long pgId,
            @RequestBody CreateRoomRequest request) {

        Room room = roomService.createRoom(pgId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Room created successfully",
                        RoomMapper.toResponse(room)
                )
        );
    }


    // ========================
    // ALL: List rooms
    // ========================

    @GetMapping("/pgs/{pgId}")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getRooms(
            @PathVariable Long pgId) {

        List<Room> rooms = roomService.getRoomsForPublicOrOwner(pgId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Rooms retrieved successfully",
                        RoomMapper.toResponse(rooms)
                )
        );
    }


    // ========================
    // OWNER: Update room
    // ========================

//    @PutMapping("/{roomId}")
//    public ResponseEntity<ApiResponse<Room>> updateRoom(
//            @PathVariable Long roomId,
//            @RequestBody Room roomRequest
//    ) {
//        Room updated = roomService.updateRoom(roomId, roomRequest);
//        return ResponseEntity.ok(
//                new ApiResponse<>(true,  "Room updated successfully", updated)
//        );
//    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
            @PathVariable Long roomId,
            @RequestBody UpdateRoomRequest request) {

        Room room = roomService.updateRoom(roomId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Room updated successfully",
                        RoomMapper.toResponse(room)
                )
        );
    }


    // ========================
    // OWNER: Remove room
    // ========================

    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long roomId) {
        roomService.disableRoom(roomId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Room disabled successfully", null)
        );
    }
}
