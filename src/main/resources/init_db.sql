CREATE SCHEMA IF NOT EXISTS user_schema;

CREATE TABLE IF NOT EXISTS user_schema.user_info(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    age INT NOT NULL
);