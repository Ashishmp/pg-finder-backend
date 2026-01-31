package com.pgfinder.pg_finder_backend.entity;

import com.pgfinder.pg_finder_backend.entity.Amenity;
import com.pgfinder.pg_finder_backend.entity.Pg;
import jakarta.persistence.*;

@Entity
@Table(
        name = "pg_amenities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"pg_id", "amenity_id"})
        }
)
public class PgAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id", nullable = false)
    private Pg pg;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pg getPg() {
        return pg;
    }

    public void setPg(Pg pg) {
        this.pg = pg;
    }

    public Amenity getAmenity() {
        return amenity;
    }

    public void setAmenity(Amenity amenity) {
        this.amenity = amenity;
    }
}
