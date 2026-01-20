package com.pgfinder.pg_finder_backend.service.impl;

import com.pgfinder.pg_finder_backend.entity.Amenity;
import com.pgfinder.pg_finder_backend.repository.AmenityRepository;
import com.pgfinder.pg_finder_backend.service.AmenityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityServiceImpl(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Override
    public List<String> getAllAmenityNames() {
        return amenityRepository.findAll()
                .stream()
                .map(Amenity::getName)
                .toList();
    }
}
