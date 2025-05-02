CREATE TABLE users
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(100)        NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

INSERT INTO users (name, email)
VALUES ('Alan Silva', 'alan@email.com');