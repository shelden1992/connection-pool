CREATE TABLE IF NOT EXISTS persons
(
    id         SERIAL       NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255),
    address    VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS notes
(
    id        SERIAL NOT NULL,
    body      TEXT,
    person_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES persons (id)
);