ALTER TABLE quizzes
ADD COLUMN time_limit_seconds INT NOT NULL DEFAULT 600;

ALTER TABLE results
ADD COLUMN time_taken_seconds INT;