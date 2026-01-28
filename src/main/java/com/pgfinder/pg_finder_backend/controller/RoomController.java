package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.request.CreateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.request.UpdateRoomRequest;
import com.pgfinder.pg_finder_backend.dto.response.PageResponse;
import com.pgfinder.pg_finder_backend.dto.response.RoomResponse;
import com.pgfinder.pg_finder_backend.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // ========================
    // OWNER: Create room
    // ========================
    @PostMapping("/pgs/{pgId}/rooms")
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(
            @PathVariable Long pgId,
            @RequestBody CreateRoomRequest request) {

        RoomResponse response = roomService.createRoom(pgId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Room created successfully",
                        response
                ));
    }

    // ========================
    // ALL: List rooms (paginated + filtered)
    // ========================
    @GetMapping("/pgs/{pgId}/rooms")
    public ResponseEntity<ApiResponse<PageResponse<RoomResponse>>> getRoomsByPg(
            @PathVariable Long pgId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "rent") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) Integer sharingType,
            @RequestParam(required = false) Boolean ac,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer minRent,
            @RequestParam(required = false) Integer maxRent) {

        PageResponse<RoomResponse> response =
                roomService.getRoomsByPg(
                        pgId,
                        page,
                        size,
                        sortBy,
                        direction,
                        sharingType,
                        ac,
                        status,
                        minRent,
                        maxRent
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Rooms retrieved successfully",
                        response
                )
        );
    }

    // ========================
    // OWNER: Update room
    // ========================
    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
            @PathVariable Long roomId,
            @RequestBody UpdateRoomRequest request) {

        RoomResponse response = roomService.updateRoom(roomId, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Room updated successfully",
                        response
                )
        );
    }

    // ========================
    // OWNER: Delete (soft) room
    // ========================
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(
            @PathVariable Long roomId) {

        roomService.deleteRoom(roomId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Room deleted successfully",
                        null
                )
        );
    }
}
