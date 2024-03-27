CREATE TABLE IF NOT EXISTS tenants
(
    id               BIGINT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    business_type_id INT          NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
