//package com.pgfinder.pg_finder_backend.entity;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "pgs")
//public class Pg {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String pgName;
//
//    @Column(nullable = false)
//    private String pgAddress;
//
//    @Column(nullable = false)
//    private String pgCity;
//
//    @Column(nullable = false)
//    private String pgState;
//
//    @Column(nullable = false)
//    private String pgCountry;
//
//    @Column(nullable = false)
//    private String pgPostalCode;
//
//    private String description;
//
//    public String getContactNumber() {
//        return contactNumber;
//    }
//
//    public void setContactNumber(String contactNumber) {
//        this.contactNumber = contactNumber;
//    }
//
//    @Column(nullable = false)
//    private String contactNumber;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getPgName() {
//        return pgName;
//    }
//
//    public void setPgName(String pgName) {
//        this.pgName = pgName;
//    }
//
//    public String getPgAddress() {
//        return pgAddress;
//    }
//
//    public void setPgAddress(String pgAddress) {
//        this.pgAddress = pgAddress;
//    }
//
//    public String getPgCity() {
//        return pgCity;
//    }
//
//    public void setPgCity(String pgCity) {
//        this.pgCity = pgCity;
//    }
//
//    public String getPgState() {
//        return pgState;
//    }
//
//    public void setPgState(String pgState) {
//        this.pgState = pgState;
//    }
//
//    public String getPgCountry() {
//        return pgCountry;
//    }
//
//    public void setPgCountry(String pgCountry) {
//        this.pgCountry = pgCountry;
//    }
//
//    public String getPgPostalCode() {
//        return pgPostalCode;
//    }
//
//    public void setPgPostalCode(String pgPostalCode) {
//        this.pgPostalCode = pgPostalCode;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id", nullable = false)
//    private User owner;
//
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//}


package com.pgfinder.pg_finder_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pgs")
public class Pg {

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

    private String status; // ACTIVE, PAUSED, DELETED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        status = "ACTIVE";
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
