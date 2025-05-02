CREATE TABLE course
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(75) NOT NULL,
    category VARCHAR(30) NOT NULL
);


INSERT INTO course (name, category)
VALUES ('Java - Beginners', 'Programming'),
       ('Java - Advanced', 'Programming'),
       ('Java - Professionals', 'Programming'),

       ('Kotlin - Beginners', 'Programming'),
       ('Kotlin - Professionals', 'Programming'),

       ('Spring Framework', 'Frameworks'),
       ('Spring Boot - Beginners', 'Frameworks'),

       ('Java EE', 'Frameworks'),

       ('JavaScript - Beginners', 'Programming'),
       ('JavaScript - Advanced', 'Programming'),

       ('React - Beginners', 'Frameworks'),
       ('React - Advanced', 'Frameworks'),

       ('Python - Beginners', 'Programming'),
       ('Django - Beginners', 'Frameworks'),

       ('Data Science with Python', 'Data Science');
