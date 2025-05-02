CREATE TABLE topic
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(100) NOT NULL,
    message    VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    course_id  BIGINT       NOT NULL,
    author_id  BIGINT       NOT NULL,
    status     VARCHAR(20)  NOT NULL,

    CONSTRAINT fk_topic_course FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT fk_topic_author FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE INDEX idx_topic_course_id ON topic (course_id);
CREATE INDEX idx_topic_author_id ON topic (author_id);
