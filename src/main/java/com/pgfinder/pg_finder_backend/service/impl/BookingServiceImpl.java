package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.entity.*;
import com.pgfinder.pg_finder_backend.enums.BookingStatus;
import com.pgfinder.pg_finder_backend.enums.RoomStatus;
import com.pgfinder.pg_finder_backend.repository.*;
import com.pgfinder.pg_finder_backend.service.BookingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final PgRepository pgRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            RoomRepository roomRepository,
            PgRepository pgRepository,
            UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.pgRepository = pgRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ===============================
    // USER requests a booking
    // ===============================
    @Override
    @Transactional
    public Booking createBooking(Long roomId, LocalDate startDate) {

        User user = getCurrentUser();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Pg pg = room.getPg();

        // Cannot book own PG
        if (pg.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot book your own PG");
        }

        // Room must be sellable
        if (room.getStatus() != RoomStatus.ACTIVE || room.getAvailableBeds() <= 0) {
            throw new RuntimeException("Room not available");
        }

        // Prevent multiple active bookings in same PG
        boolean alreadyBooked = bookingRepository.existsByUserAndPgAndStatusIn(
                user,
                pg,
                List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED, BookingStatus.CHECKED_IN)
        );
        if (alreadyBooked) {
            throw new RuntimeException("You already have an active booking in this PG");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setPg(pg);
        booking.setRoom(room);
        booking.setStartDate(startDate);
        booking.setPrice(room.getRent());
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    // ===============================
    // OWNER approves booking
    // ===============================
    @Override
    @Transactional
    public Booking approveBooking(Long bookingId) {

        User owner = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Pg pg = booking.getPg();
        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You do not own this PG");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking cannot be approved");
        }

        Room room = booking.getRoom();

        // Lock bed
        if (room.getAvailableBeds() <= 0) {
            throw new RuntimeException("No beds left");
        }

        room.setAvailableBeds(room.getAvailableBeds() - 1);
        roomRepository.save(room);

        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    // ===============================
    // USER or OWNER cancels
    // ===============================
    @Override
    @Transactional
    public Booking cancelBooking(Long bookingId) {

        User user = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean isOwner = booking.getPg().getOwner().getId().equals(user.getId());
        boolean isUser = booking.getUser().getId().equals(user.getId());

        if (!isOwner && !isUser) {
            throw new RuntimeException("You cannot cancel this booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED ||
                booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Booking already closed");
        }

        // If bed was locked, release it
        if (booking.getStatus() == BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Checked-in booking must be vacated, not cancelled");
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            Room room = booking.getRoom();
            room.setAvailableBeds(room.getAvailableBeds() + 1);
            roomRepository.save(room);
        }


        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    // ===============================
    // USER dashboard
    // ===============================
    @Override
    public List<Booking> getMyBookings() {
        return bookingRepository.findByUser(getCurrentUser());
    }

    // ===============================
    // OWNER dashboard
    // ===============================
    @Override
    public List<Booking> getBookingsForMyPgs() {
        User owner = getCurrentUser();
        return bookingRepository.findByPgOwner(owner);
    }

    // ===============================
    // OWNER and ADMIN can only fill check in date
    // ===============================

    @Override
    @Transactional
    public Booking checkInBooking(Long bookingId) {

        User owner = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean isOwner = booking.getPg().getOwner().getId().equals(owner.getId());
        boolean isAdmin = owner.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not allowed to check in");
        }

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Booking must be approved before check-in");
        }

        booking.setStatus(BookingStatus.CHECKED_IN);
        return bookingRepository.save(booking);
    }


    // ===============================
    // OWNER and ADMIN can only fill checkout date
    // ===============================

    @Override
    @Transactional
    public Booking vacateBooking(Long bookingId) {

        User currentUser = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Only OWNER or ADMIN can vacate
        boolean isOwner = booking.getPg().getOwner().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not allowed to vacate this booking");
        }

        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Only checked-in bookings can be vacated");
        }

        // Set checkout date
        booking.setEndDate(LocalDate.now());
        booking.setStatus(BookingStatus.COMPLETED);

        // Release bed
        Room room = booking.getRoom();
        room.setAvailableBeds(room.getAvailableBeds() + 1);
        roomRepository.save(room);

        return bookingRepository.save(booking);
    }

}
