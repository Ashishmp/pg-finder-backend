CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,

                       name VARCHAR(150) NOT NULL,
                       email VARCHAR(150) NOT NULL,
                       password TEXT NOT NULL,
                       phone VARCHAR(20) NOT NULL,

                       role VARCHAR(30) NOT NULL,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP,
                       last_login_at TIMESTAMP,

                       CONSTRAINT uq_users_email UNIQUE (email),
                       CONSTRAINT uq_users_phone UNIQUE (phone)
);
