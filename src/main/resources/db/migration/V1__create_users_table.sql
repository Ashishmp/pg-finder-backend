CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       name VARCHAR(100) NOT NULL,

                       email VARCHAR(150) NOT NULL UNIQUE,

                       password VARCHAR(255) NOT NULL,

                       phone VARCHAR(20) NOT NULL UNIQUE,

                       role VARCHAR(30) NOT NULL,

                       status VARCHAR(20) NOT NULL,

                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NULL,
                       last_login_at TIMESTAMP NULL
);
