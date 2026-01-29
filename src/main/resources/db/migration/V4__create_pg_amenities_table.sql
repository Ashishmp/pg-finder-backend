CREATE TABLE pg_amenities (
                              pg_id BIGINT NOT NULL,
                              amenity_id BIGINT NOT NULL,

                              PRIMARY KEY (pg_id, amenity_id),

                              CONSTRAINT fk_pg_amenities_pg
                                  FOREIGN KEY (pg_id) REFERENCES pgs(id) ON DELETE CASCADE,

                              CONSTRAINT fk_pg_amenities_amenity
                                  FOREIGN KEY (amenity_id) REFERENCES amenities(id) ON DELETE CASCADE
);
