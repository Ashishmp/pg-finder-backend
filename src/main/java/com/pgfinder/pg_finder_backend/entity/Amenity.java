package com.pgfinder.pg_finder_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
