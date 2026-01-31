package com.pgfinder.pg_finder_backend.service.analytics;

import com.pgfinder.pg_finder_backend.dto.response.analytics.*;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.repository.analytics.OwnerDashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OwnerDashboardService {

    private final OwnerDashboardRepository repository;
    private final UserRepository userRepository;


    public OwnerDashboardResponse getDashboard() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long ownerId = owner.getId();
//        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDateTime startOfMonth =
                LocalDate.now()
                        .withDayOfMonth(1)
                        .atStartOfDay();

        return new OwnerDashboardResponse(
                new PortfolioMetrics(
                        repository.countPgs(ownerId),
                        repository.countRooms(ownerId)
                ),
                new BookingMetrics(
                        repository.totalBookings(ownerId),
                        repository.monthlyBookings(ownerId, startOfMonth),
                        repository.activeBookings(ownerId),
                        repository.completedBookings(ownerId)
                ),
                new RevenueMetrics(
                        repository.totalRevenue(ownerId),
                        repository.monthlyRevenue(ownerId, startOfMonth)
                ),
                new RatingMetrics(
                        repository.averageRating(ownerId),
                        repository.totalReviews(ownerId)
                )
        );
    }
}
