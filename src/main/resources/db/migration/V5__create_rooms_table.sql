CREATE TABLE rooms (
                       id BIGSERIAL PRIMARY KEY,

                       pg_id BIGINT NOT NULL,

                       sharing_type INT NOT NULL,
                       rent DOUBLE PRECISION NOT NULL,

                       total_beds INT NOT NULL,
                       available_beds INT NOT NULL,

                       is_ac BOOLEAN NOT NULL,

                       status VARCHAR(30) NOT NULL,

                       version BIGINT NOT NULL,

                       created_at TIMESTAMP NOT NULL,

                       CONSTRAINT fk_room_pg
                           FOREIGN KEY (pg_id) REFERENCES pgs(id) ON DELETE CASCADE,

                       CONSTRAINT uq_room_pg_sharing_ac
                           UNIQUE (pg_id, sharing_type, is_ac)
);

