package com.pgfinder.pg_finder_backend.service.payment.Impl;

import com.pgfinder.pg_finder_backend.entity.Booking;
import com.pgfinder.pg_finder_backend.entity.Payment;
import com.pgfinder.pg_finder_backend.enums.PaymentStatus;
import com.pgfinder.pg_finder_backend.repository.BookingRepository;
import com.pgfinder.pg_finder_backend.repository.payment.PaymentRepository;
import com.pgfinder.pg_finder_backend.service.payment.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public void simulatePayment(Long bookingId, PaymentStatus status) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment;
        payment = paymentRepository.findByBookingId(bookingId)
                .orElseGet(() -> {
                    Payment p = new Payment();
                    p.setBooking(booking);
                    p.setAmount(booking.getPrice());
                    p.setStatus(PaymentStatus.PENDING);
                    return p;
                });

        payment.setStatus(status);
        paymentRepository.save(payment);
    }
}
