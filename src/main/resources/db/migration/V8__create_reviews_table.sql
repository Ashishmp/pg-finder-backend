CREATE TABLE reviews (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,

                         booking_id BIGINT NOT NULL,
                         pg_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,

                         rating INT NOT NULL,
                         comment TEXT,

                         created_at TIMESTAMP NOT NULL,

                         CONSTRAINT uq_review_booking UNIQUE (booking_id),

                         CONSTRAINT fk_review_booking
                             FOREIGN KEY (booking_id) REFERENCES bookings(id),

                         CONSTRAINT fk_review_pg
                             FOREIGN KEY (pg_id) REFERENCES pgs(id),

                         CONSTRAINT fk_review_user
                             FOREIGN KEY (user_id) REFERENCES users(id)
);
