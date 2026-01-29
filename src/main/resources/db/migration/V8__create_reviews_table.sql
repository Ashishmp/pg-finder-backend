CREATE TABLE reviews (
                         id BIGSERIAL PRIMARY KEY,

                         booking_id BIGINT NOT NULL UNIQUE,
                         pg_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,

                         rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                         comment VARCHAR(1000),

                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_review_booking
                             FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,

                         CONSTRAINT fk_review_pg
                             FOREIGN KEY (pg_id) REFERENCES pgs(id) ON DELETE CASCADE,

                         CONSTRAINT fk_review_user
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
