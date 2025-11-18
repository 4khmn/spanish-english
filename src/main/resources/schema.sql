CREATE TABLE IF NOT EXISTS words (
    id SERIAL PRIMARY KEY,
    english VARCHAR(100) NOT NULL,
    spanish VARCHAR(100) NOT NULL,
    unit INT,
    part_of_speech VARCHAR(100) NOT NULL,
    category VARCHAR(100)
);