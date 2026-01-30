
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255),
    provider VARCHAR(50), --OAuth Provider
    is_active BOOLEAN NOT NULL,
    role VARCHAR(20) NOT NULL
        CHECK (role IN ('ROLE_PLAYER', 'ROLE_GAMEMASTER'))
);

CREATE TABLE quizzes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    user_id BIGINT,
    CONSTRAINT fk_quiz_user FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    points INT,
    order_index INT,
    quiz_id BIGINT,
    CONSTRAINT fk_question_quiz FOREIGN KEY(quiz_id) REFERENCES quizzes(id)
);
CREATE TABLE answers (
    id BIGSERIAL PRIMARY KEY,
    option_answer TEXT,
    is_correct BOOLEAN,
    question_id BIGINT,
    CONSTRAINT fk_answer_question FOREIGN KEY(question_id) REFERENCES questions(id)
);

CREATE TABLE results (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    quiz_id BIGINT,
    score INT,
    total_score INT,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    CONSTRAINT fk_result_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_result_quiz FOREIGN KEY(quiz_id) REFERENCES quizzes(id)
);