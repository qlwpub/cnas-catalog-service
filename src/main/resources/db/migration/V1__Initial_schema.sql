-- Flyway expects SQL migration files to comply with a specific naming pattern.
--  * Prefix—V for versioned migrations
--  * Version—Version number using dots or underscores to separate it into multiple parts (e.g., 2.0.1)
--  * Separator—TWO underscores: __
--  * Description—Words separated by underscores
--  * Suffix—.sql

CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price FLOAT8 NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP NOT NULL,
    version INTEGER NOT NULL
);