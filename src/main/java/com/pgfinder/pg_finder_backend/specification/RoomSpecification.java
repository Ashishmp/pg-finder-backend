package com.pgfinder.pg_finder_backend.specification;

import com.pgfinder.pg_finder_backend.entity.Room;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {

    public static Specification<Room> byPg(Long pgId) {
        return (root, query, cb) ->
                cb.equal(root.get("pg").get("id"), pgId);
    }

    public static Specification<Room> hasSharingType(Integer sharingType) {
        return (root, query, cb) ->
                sharingType == null ? null :
                        cb.equal(root.get("sharingType"), sharingType);
    }

    public static Specification<Room> hasAc(Boolean ac) {
        return (root, query, cb) ->
                ac == null ? null :
                        cb.equal(root.get("ac"), ac);
    }

    public static Specification<Room> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("status"), status);
    }

    public static Specification<Room> rentBetween(
            Integer minRent,
            Integer maxRent) {

        return (root, query, cb) -> {
            if (minRent == null && maxRent == null) {
                return null;
            }
            if (minRent != null && maxRent != null) {
                return cb.between(root.get("rent"), minRent, maxRent);
            }
            if (minRent != null) {
                return cb.greaterThanOrEqualTo(root.get("rent"), minRent);
            }
            return cb.lessThanOrEqualTo(root.get("rent"), maxRent);
        };
    }
}

