package com.pgfinder.pg_finder_backend.specification;

import com.pgfinder.pg_finder_backend.dto.request.PgSearchRequest;
import com.pgfinder.pg_finder_backend.entity.Amenity;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.Room;
import com.pgfinder.pg_finder_backend.enums.PgStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PgSpecification {

    public static Specification<Pg> withFilters(PgSearchRequest request) {

        return (root, query, cb) -> {

            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            // Only ACTIVE PGs
            predicates.add(cb.equal(root.get("status"), PgStatus.ACTIVE));

            // City filter
            if (request.getCity() != null && !request.getCity().isBlank()) {
                predicates.add(
                        cb.equal(
                                cb.lower(root.get("pgCity")),
                                request.getCity().toLowerCase()
                        )
                );
            }

            // Room join
            Join<Pg, Room> roomJoin = root.join("rooms", JoinType.INNER);

            // Only available rooms
            predicates.add(cb.greaterThan(roomJoin.get("availableBeds"), 0));

            if (request.getIsAc() != null) {
                predicates.add(cb.equal(roomJoin.get("isAc"), request.getIsAc()));
            }

            if (request.getSharingType() != null) {
                predicates.add(cb.equal(roomJoin.get("sharingType"), request.getSharingType()));
            }

            if (request.getMinRent() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(roomJoin.get("rent"), request.getMinRent())
                );
            }

            if (request.getMaxRent() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(roomJoin.get("rent"), request.getMaxRent())
                );
            }

            if (request.getAmenities() != null && !request.getAmenities().isEmpty()) {
                Join<Pg, Amenity> amenityJoin = root.join("amenities", JoinType.INNER);
                predicates.add(amenityJoin.get("name").in(request.getAmenities()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
