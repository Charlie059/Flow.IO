CREATE TABLE IF NOT EXISTS tenants
(
    id               BIGINT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    business_type_id INT          NOT NULL,
    create_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
