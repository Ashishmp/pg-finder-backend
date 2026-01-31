package com.pgfinder.pg_finder_backend.service.payment;

import com.pgfinder.pg_finder_backend.enums.PaymentStatus;

public interface PaymentService {
    void simulatePayment(Long bookingId, PaymentStatus status);
}
