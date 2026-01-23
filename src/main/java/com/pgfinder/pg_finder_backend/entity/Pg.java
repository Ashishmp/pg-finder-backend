package com.pgfinder.pg_finder_backend.entity;

import com.pgfinder.pg_finder_backend.enums.PgStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pgs")
public class Pg {
    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    @ManyToMany
    @JoinTable(
            name = "pg_amenities",
            joinColumns = @JoinColumn(name = "pg_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

    @Column(length = 1000)
    private String rules;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pgName;

    @Column(nullable = false)
    private String pgAddress;

    @Column(nullable = false)
    private String pgCity;

    @Column(nullable = false)
    private String pgState;

    @Column(nullable = false)
    private String pgCountry;

    @Column(nullable = false)
    private String pgPostalCode;

    private String description;

    @Column(nullable = false)
    private String contactNumber;

    // 🔐 OWNER OF THIS PG
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PgStatus status;
   // ACTIVE, PAUSED, DELETED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        status = PgStatus.PENDING;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    private Double averageRating = 0.0;
    private Integer totalReviews = 0;


    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgAddress() {
        return pgAddress;
    }

    public void setPgAddress(String pgAddress) {
        this.pgAddress = pgAddress;
    }

    public String getPgCity() {
        return pgCity;
    }

    public void setPgCity(String pgCity) {
        this.pgCity = pgCity;
    }

    public String getPgState() {
        return pgState;
    }

    public void setPgState(String pgState) {
        this.pgState = pgState;
    }

    public String getPgCountry() {
        return pgCountry;
    }

    public void setPgCountry(String pgCountry) {
        this.pgCountry = pgCountry;
    }

    public String getPgPostalCode() {
        return pgPostalCode;
    }

    public void setPgPostalCode(String pgPostalCode) {
        this.pgPostalCode = pgPostalCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PgStatus getStatus() {
        return status;
    }

    public void setStatus(PgStatus status) {
        this.status = status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }
}
