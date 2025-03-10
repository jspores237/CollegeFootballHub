
CREATE TABLE IF NOT EXISTS team (
    id BIGINT PRIMARY KEY,
    version BIGINT,
    school VARCHAR(255),
    mascot VARCHAR(255),
    abbreviation VARCHAR(255),
    alternate_names VARCHAR(255),
    conference VARCHAR(255),
    division VARCHAR(255),
    classification VARCHAR(255),
    color VARCHAR(255),
    alternate_color VARCHAR(255),
    logos VARCHAR(1000),
    twitter VARCHAR(255),
    location_city VARCHAR(255),
    location_state VARCHAR(255),
    location_latitude DOUBLE PRECISION,
    location_longitude DOUBLE PRECISION
    );

