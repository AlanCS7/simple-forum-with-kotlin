CREATE TABLE answer
(
    id         BIGSERIAL PRIMARY KEY,
    message    VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id  BIGINT       NOT NULL,
    topic_id   BIGINT       NOT NULL,
    solution   BOOLEAN   DEFAULT FALSE,

    CONSTRAINT fk_answer_author FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT fk_answer_topic FOREIGN KEY (topic_id) REFERENCES topic (id)
);

CREATE INDEX idx_answer_author_id ON answer (author_id);
CREATE INDEX idx_answer_topic_id ON answer (topic_id);
