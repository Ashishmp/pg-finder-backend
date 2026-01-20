package com.pgfinder.pg_finder_backend.specification;

import com.pgfinder.pg_finder_backend.dto.request.PgSearchRequest;
import com.pgfinder.pg_finder_backend.entity.Amenity;
import com.pgfinder.pg_finder_backend.entity.Pg;
import com.pgfinder.pg_finder_backend.entity.Room;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class PgSpecification {

    public static Specification<Pg> withFilters(PgSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // only ACTIVE PGs
            predicates.add(cb.equal(root.get("status"), "ACTIVE"));

            if (request.getCity() != null) {
                predicates.add(
                        cb.equal(
                                cb.lower(root.get("pgCity")),
                                request.getCity().toLowerCase()
                        )
                );
            }

            if (request.getIsAc() != null) {
                Join<Pg, Room> roomJoin = root.join("rooms", JoinType.LEFT);
                predicates.add(cb.equal(roomJoin.get("isAc"), request.getIsAc()));
            }

            if (request.getSharingType() != null) {
                Join<Pg, Room> roomJoin = root.join("rooms", JoinType.LEFT);
                predicates.add(cb.equal(roomJoin.get("sharingType"), request.getSharingType()));
            }

            if (request.getMinRent() != null) {
                Join<Pg, Room> roomJoin = root.join("rooms", JoinType.LEFT);
                predicates.add(cb.greaterThanOrEqualTo(
                        roomJoin.get("rent"), request.getMinRent()));
            }

            if (request.getMaxRent() != null) {
                Join<Pg, Room> roomJoin = root.join("rooms", JoinType.LEFT);
                predicates.add(cb.lessThanOrEqualTo(
                        roomJoin.get("rent"), request.getMaxRent()));
            }

            if (request.getAmenities() != null && !request.getAmenities().isEmpty()) {
                Join<Pg, Amenity> amenityJoin = root.join("amenities");
                predicates.add(amenityJoin.get("name").in(request.getAmenities()));
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

