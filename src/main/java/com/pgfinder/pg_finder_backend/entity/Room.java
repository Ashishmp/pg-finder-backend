
package com.pgfinder.pg_finder_backend.entity;

import com.pgfinder.pg_finder_backend.enums.RoomStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "rooms",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"pg_id", "sharingType", "isAc"})
        }
)

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pg_id")
    private Pg pg;
    @Version
    private Long version;
    private Integer sharingType; // 1,2,3
    private Double rent;
    private Integer totalBeds;
    private Integer availableBeds;
//    public Boolean isAc;

    private LocalDateTime createdAt;
    @Column(name = "is_ac", nullable = false)
    private Boolean ac;


    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    private RoomStatus status;   // ACTIVE, INACTIVE

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

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

    public Integer getSharingType() {
        return sharingType;
    }

    public void setSharingType(Integer sharingType) {
        this.sharingType = sharingType;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Integer getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(Integer totalBeds) {
        this.totalBeds = totalBeds;
    }

    public Integer getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(Integer availableBeds) {
        this.availableBeds = availableBeds;
    }
    public Boolean getAc() {
        return ac;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
