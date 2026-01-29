CREATE TABLE pgs (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,

                     pg_name VARCHAR(255) NOT NULL,
                     pg_address VARCHAR(500) NOT NULL,
                     pg_city VARCHAR(100) NOT NULL,
                     pg_state VARCHAR(100) NOT NULL,
                     pg_country VARCHAR(100) NOT NULL,
                     pg_postal_code VARCHAR(20) NOT NULL,

                     description TEXT,

                     contact_number VARCHAR(20) NOT NULL,

                     owner_id BIGINT NOT NULL,

                     status VARCHAR(30) NOT NULL,

                     average_rating DOUBLE DEFAULT 0.0,
                     total_reviews INT DEFAULT 0,

                     rules VARCHAR(1000),

                     created_at TIMESTAMP NOT NULL,
                     updated_at TIMESTAMP NULL,

                     CONSTRAINT fk_pg_owner
                         FOREIGN KEY (owner_id) REFERENCES users(id)
);
