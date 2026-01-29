CREATE TABLE pgs (
                     id BIGSERIAL PRIMARY KEY,

                     pg_name VARCHAR(200) NOT NULL,
                     pg_address VARCHAR(300) NOT NULL,
                     pg_city VARCHAR(100) NOT NULL,
                     pg_state VARCHAR(100) NOT NULL,
                     pg_country VARCHAR(100) NOT NULL,
                     pg_postal_code VARCHAR(20) NOT NULL,

                     description VARCHAR(1000),
                     contact_number VARCHAR(20) NOT NULL,

                     owner_id BIGINT NOT NULL,

                     status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

                     average_rating DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                     total_reviews INT NOT NULL DEFAULT 0,

                     rules TEXT,

                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP,

                     CONSTRAINT fk_pgs_owner
                         FOREIGN KEY (owner_id) REFERENCES users(id)
);
