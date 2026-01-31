CREATE TABLE pg_amenities (
                              id BIGSERIAL PRIMARY KEY,
                              pg_id BIGINT NOT NULL,
                              amenity_id BIGINT NOT NULL,

                              CONSTRAINT uk_pg_amenity UNIQUE (pg_id, amenity_id),
                              CONSTRAINT fk_pg FOREIGN KEY (pg_id) REFERENCES pgs(id),
                              CONSTRAINT fk_amenity FOREIGN KEY (amenity_id) REFERENCES amenities(id)
);
