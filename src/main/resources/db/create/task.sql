CREATE TABLE task(
                     id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                     title VARCHAR NOT NULL,
                     description TEXT,
                     status VARCHAR NOT NULL,
                     created_at TIMESTAMP NOT NULL,
                     tag VARCHAR NOT NULL,
                     user_id INT NOT NULL
);
INSERT INTO task (title, description, status, created_at, tag, user_id)
VALUES ('задачка', 'Пример описания задачи', 'TODO', CURRENT_TIMESTAMP, 'important', 1);