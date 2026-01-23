package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.dto.response.OwnerAnalyticsResponse;
import com.pgfinder.pg_finder_backend.entity.User;
import com.pgfinder.pg_finder_backend.repository.BookingRepository;
import com.pgfinder.pg_finder_backend.repository.PgRepository;
import com.pgfinder.pg_finder_backend.repository.RoomRepository;
import com.pgfinder.pg_finder_backend.repository.UserRepository;
import com.pgfinder.pg_finder_backend.service.AnalyticsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


    @Service
    public class AnalyticsServiceImpl implements AnalyticsService {

        private final PgRepository pgRepository;
        private final RoomRepository roomRepository;
        private final BookingRepository bookingRepository;
        private final UserRepository userRepository;

        public AnalyticsServiceImpl(
                PgRepository pgRepository,
                RoomRepository roomRepository,
                BookingRepository bookingRepository,
                UserRepository userRepository) {

            this.pgRepository = pgRepository;
            this.roomRepository = roomRepository;
            this.bookingRepository = bookingRepository;
            this.userRepository = userRepository;
        }

        private User getCurrentUser() {
            String email = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        @Override
        public OwnerAnalyticsResponse getOwnerSummary() {

            User owner = getCurrentUser();

            long totalPgs = pgRepository.countByOwnerId(owner.getId());
            long totalRooms = roomRepository.countByPg_Owner_Id(owner.getId());
            long totalBeds = roomRepository.totalBeds(owner.getId());
            long occupiedBeds = bookingRepository.countActiveBookings(owner.getId());
            double revenue = bookingRepository.totalRevenue(owner.getId());

            double occupancyRate = totalBeds == 0
                    ? 0
                    : (occupiedBeds * 100.0) / totalBeds;

            OwnerAnalyticsResponse res = new OwnerAnalyticsResponse();
            res.setTotalPgs(totalPgs);
            res.setTotalRooms(totalRooms);
            res.setTotalBeds(totalBeds);
            res.setOccupiedBeds(occupiedBeds);
            res.setOccupancyRate(occupancyRate);
            res.setMonthlyRevenue(revenue);

            return res;
        }
    }
