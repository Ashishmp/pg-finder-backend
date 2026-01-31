package com.pgfinder.pg_finder_backend.controller.payment;

import com.pgfinder.pg_finder_backend.dto.common.ApiResponse;
import com.pgfinder.pg_finder_backend.enums.PaymentStatus;
import com.pgfinder.pg_finder_backend.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/payments")
    @RequiredArgsConstructor
    public class PaymentController {

        private final PaymentService paymentService;

        // ⚠️ DEV / TEST ONLY
        @PostMapping("/{bookingId}/simulate")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<String>> simulatePayment(
                @PathVariable Long bookingId,
                @RequestParam PaymentStatus status
        ) {
            paymentService.simulatePayment(bookingId, status);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Payment status updated", null)
            );
        }
    }


