package com.pgfinder.pg_finder_backend.service;

import com.pgfinder.pg_finder_backend.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking createBooking(Long roomId, LocalDate startDate);

    Booking approveBooking(Long bookingId);

    Booking cancelBooking(Long bookingId);

    List<Booking> getMyBookings();

    List<Booking> getBookingsForMyPgs();
}
