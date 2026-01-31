CREATE TABLE bookings (
                          id BIGSERIAL PRIMARY KEY,

                          user_id BIGINT NOT NULL,
                          pg_id BIGINT NOT NULL,
                          room_id BIGINT NOT NULL,

                          start_date DATE NOT NULL,
                          end_date DATE,

                          price DOUBLE PRECISION NOT NULL,

                          status VARCHAR(30) NOT NULL,

                          created_at TIMESTAMP NOT NULL,

                          CONSTRAINT fk_booking_user
                              FOREIGN KEY (user_id) REFERENCES users(id),

                          CONSTRAINT fk_booking_pg
                              FOREIGN KEY (pg_id) REFERENCES pgs(id),

                          CONSTRAINT fk_booking_room
                              FOREIGN KEY (room_id) REFERENCES rooms(id)
);

CREATE INDEX idx_booking_user ON bookings(user_id);
CREATE INDEX idx_booking_pg ON bookings(pg_id);
CREATE INDEX idx_booking_room ON bookings(room_id);
