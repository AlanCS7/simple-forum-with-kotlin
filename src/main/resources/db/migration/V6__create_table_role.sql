CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO role (name)
VALUES ('READ_ONLY'),
       ('READ_WRITE');
