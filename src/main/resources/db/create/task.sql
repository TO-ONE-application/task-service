CREATE TABLE task(
                     id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                     title VARCHAR NOT NULL,
                     description TEXT,
                     status VARCHAR NOT NULL,
                     created_at TIMESTAMP NOT NULL,
                     tag VARCHAR NOT NULL,
                     user_id INT NOT NULL
)