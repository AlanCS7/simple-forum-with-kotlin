DELETE FROM users;
DELETE FROM course;

INSERT INTO users (id, name, email) VALUES (1, 'Alan Silva', 'alan@email.com');
INSERT INTO course (id, name, category) VALUES (1, 'Kotlin Basics', 'Programming');