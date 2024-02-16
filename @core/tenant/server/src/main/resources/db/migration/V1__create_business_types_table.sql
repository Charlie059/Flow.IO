CREATE TABLE IF NOT EXISTS business_types
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO business_types (name)
VALUES ('Sole Proprietorship'),
       ('General Partnership'),
       ('Limited Partnership (LP)'),
       ('Corporation'),
       ('Limited Liability Company (LLC)'),
       ('Nonprofit Organization'),
       ('Cooperative (Co-op)');
