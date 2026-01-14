package com.pgfinder.pg_finder_backend.mapper;

import com.pgfinder.pg_finder_backend.dto.response.BookingResponse;
import com.pgfinder.pg_finder_backend.entity.Booking;

import java.util.List;

public class BookingMapper {

    public static BookingResponse toResponse(Booking booking) {
        BookingResponse dto = new BookingResponse();

        dto.setBookingId(booking.getId());
        dto.setStatus(booking.getStatus().name());
        dto.setStartDate(booking.getStartDate().toString());
        dto.setEndDate(booking.getEndDate() != null ? booking.getEndDate().toString() : null);
        dto.setPrice(booking.getPrice());

        dto.setRoomId(booking.getRoom().getId());
        dto.setSharingType(booking.getRoom().getSharingType());
        dto.setAc(booking.getRoom().getAc());

        dto.setPgId(booking.getPg().getId());
        dto.setPgName(booking.getPg().getPgName());
        dto.setPgCity(booking.getPg().getPgCity());

        return dto;
    }
    public static List<BookingResponse> toResponse(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toResponse)
                .toList();
    }

}
