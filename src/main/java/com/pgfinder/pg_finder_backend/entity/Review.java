package com.pgfinder.pg_finder_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pg pg;

    @ManyToOne
    private User user;

    private Integer rating;
    private String comment;

    private LocalDateTime createdAt;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}



//
//🗄️ PG FINDER – DATABASE TABLES
//✅ CORE / MVP TABLES (CREATE FIRST)
//These are non-negotiable. Your backend depends on them.
//
//1️⃣ users
//Stores all users (USER / OWNER / ADMIN)
//
//Key columns
//
//id (PK)
//
//name
//
//email (unique)
//
//phone
//
//        password_hash
//
//role (USER, OWNER, ADMIN)
//
//status (ACTIVE, INACTIVE)
//
//city
//
//        created_at
//
//updated_at
//
//        last_login_at
//
//📌 Analytics later: user growth, city-wise demand
//
//2️⃣ pgs
//Main PG/property table
//
//Key columns
//
//id (PK)
//
//owner_id (FK → users.id)
//
//name
//
//        description
//
//gender_allowed
//
//        total_rooms
//
//status (ACTIVE, PAUSED, DELETED)
//
//created_at
//
//        updated_at
//
//3️⃣ pg_addresses
//Separated for clean design & search
//
//Key columns
//
//id (PK)
//
//pg_id (FK)
//
//city
//
//        area
//
//address_line
//
//        latitude
//
//longitude
//
//        pincode
//
//📌 Helps with search & location analytics
//
//4️⃣ rooms
//Room-level inventory
//
//Key columns
//
//id (PK)
//
//pg_id (FK)
//
//sharing_type (1,2,3)
//
//rent
//
//        total_beds
//
//available_beds
//
//        is_ac
//
//created_at
//
//5️⃣ bookings
//Tracks user bookings
//
//Key columns
//
//id (PK)
//
//user_id (FK)
//
//room_id (FK)
//
//pg_id (FK)
//
//start_date
//
//        end_date
//
//status (BOOKED, CANCELLED, COMPLETED)
//
//created_at
//
//📌 Fact table for analytics later
//
//6️⃣ payments
//Simulated payment tracking
//
//Key columns
//
//id (PK)
//
//booking_id (FK)
//
//amount
//
//        payment_mode
//
//status (INITIATED, SUCCESS, FAILED)
//
//transaction_ref
//
//        created_at
//
//🟡 SUPPORTING TABLES (IMPORTANT BUT SIMPLE)
//7️⃣ amenities
//Master table
//
//Key columns
//
//id
//
//        name
//
//icon
//
//8️⃣ pg_amenities
//Many-to-many mapping
//
//Key columns
//
//pg_id (FK)
//
//amenity_id (FK)
//
//9️⃣ reviews
//User feedback
//
//Key columns
//
//id
//
//        pg_id
//
//user_id
//
//        rating
//
//comment
//
//        created_at
//
//🔵 OPERATIONAL / SYSTEM TABLES
//🔟 roles (optional if enum-based)
//If you don’t want hardcoded roles.
//
//        1️⃣1️⃣ audit_logs
//Very good for enterprise feel
//
//Key columns
//
//id
//
//        user_id
//
//action
//
//        entity
//
//entity_id
//
//        created_at
//
//🔵 FUTURE / ANALYTICS-READY TABLES (DON’T BUILD NOW)
//These will be built in Databricks, not MySQL.
//
//        fact_bookings
//
//        fact_payments
//
//dim_user
//
//        dim_pg
//
//dim_location
//
//        daily_pg_metrics