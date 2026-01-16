package com.pgfinder.pg_finder_backend.controller;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.dto.response.BookingResponse;
import com.pgfinder.pg_finder_backend.entity.Booking;
import com.pgfinder.pg_finder_backend.mapper.BookingMapper;
import com.pgfinder.pg_finder_backend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ==============================
    // USER: Request a booking
    // ==============================
    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @PathVariable Long roomId,
            @RequestParam LocalDate startDate

    ) {
        Booking booking = bookingService.createBooking(roomId, startDate);
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Booking requested successfully", BookingMapper.toResponse(booking)));
    }

    // ==============================
    // OWNER: Approve booking
    // ==============================
    @PutMapping("/{bookingId}/approve")
    public ResponseEntity<ApiResponse<BookingResponse>> approveBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.approveBooking(bookingId);
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Booking approved successfully", BookingMapper.toResponse(booking))
        );
    }

    // ==============================
    // USER or OWNER: Cancel booking
    // ==============================
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Booking cancelled successfully", BookingMapper.toResponse(booking))
        );
    }

    // ==============================
    // USER: My bookings
    // ==============================
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> myBookings() {
        List<Booking> bookings = bookingService.getMyBookings();
        return ResponseEntity.ok(
                new ApiResponse<>(true,"My bookings requested successfully", BookingMapper.toResponse(bookings))
        );
    }

    // ==============================
    // OWNER: Bookings for my PGs
    // ==============================
    @GetMapping("/owner")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> bookingsForMyPgs() {
        List<Booking> bookings = bookingService.getBookingsForMyPgs();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "My bookings requested successfully", BookingMapper.toResponse(bookings))
        );
    }

    // ==============================
    // OWNER/ADMIN: add checkin date
    // ==============================

    @PutMapping("/{bookingId}/check-in")
    public ResponseEntity<ApiResponse<BookingResponse>> checkInBooking(
            @PathVariable Long bookingId) {

        Booking booking = bookingService.checkInBooking(bookingId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Tenant checked in successfully",
                        BookingMapper.toResponse(booking))
        );
    }

    // ==============================
    // OWNER/ADMIN: vacate tenant
    // ==============================

    @PutMapping("/{bookingId}/vacate")
    public ResponseEntity<ApiResponse<BookingResponse>> vacateBooking(
            @PathVariable Long bookingId) {

        Booking booking = bookingService.vacateBooking(bookingId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Tenant vacated successfully",
                        BookingMapper.toResponse(booking)
                )
        );
    }

}
