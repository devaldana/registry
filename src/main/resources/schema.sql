DROP TABLE IF EXISTS phones CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    is_active BOOLEAN,
    created_at TIMESTAMP(6) WITH TIME ZONE,
    last_login_at TIMESTAMP(6) WITH TIME ZONE,
    modified_at TIMESTAMP(6) WITH TIME ZONE,
    password VARCHAR(255),
    token VARCHAR(255)
);

CREATE TABLE phones (
    id UUID PRIMARY KEY,
    city_code VARCHAR(255),
    country_code VARCHAR(255),
    number VARCHAR(255),
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
