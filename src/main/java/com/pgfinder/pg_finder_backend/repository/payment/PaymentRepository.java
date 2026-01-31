package com.pgfinder.pg_finder_backend.repository.payment;

import com.pgfinder.pg_finder_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBookingId(Long bookingId);
}

