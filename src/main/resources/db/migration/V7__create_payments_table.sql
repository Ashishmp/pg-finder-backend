CREATE TABLE payments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,

                          booking_id BIGINT NOT NULL,

                          amount DOUBLE NOT NULL,
                          payment_mode VARCHAR(50),
                          status VARCHAR(30) NOT NULL,
                          transaction_ref VARCHAR(100),

                          created_at TIMESTAMP NOT NULL,

                          CONSTRAINT fk_payment_booking
                              FOREIGN KEY (booking_id) REFERENCES bookings(id),

                          CONSTRAINT uq_payment_booking
                              UNIQUE (booking_id)
);
