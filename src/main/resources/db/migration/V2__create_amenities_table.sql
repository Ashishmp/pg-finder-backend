CREATE TABLE amenities (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL UNIQUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
