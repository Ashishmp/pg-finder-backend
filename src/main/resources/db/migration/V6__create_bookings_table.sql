CREATE TABLE bookings (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,

                          user_id BIGINT NOT NULL,
                          pg_id BIGINT NOT NULL,
                          room_id BIGINT NOT NULL,

                          start_date DATE NOT NULL,
                          end_date DATE NOT NULL,

                          price DOUBLE,

                          status VARCHAR(30) NOT NULL,
                          created_at TIMESTAMP NOT NULL,

                          CONSTRAINT fk_booking_user
                              FOREIGN KEY (user_id) REFERENCES users(id),

                          CONSTRAINT fk_booking_pg
                              FOREIGN KEY (pg_id) REFERENCES pgs(id),

                          CONSTRAINT fk_booking_room
                              FOREIGN KEY (room_id) REFERENCES rooms(id)
);
