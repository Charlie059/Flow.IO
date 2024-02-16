CREATE TABLE IF NOT EXISTS tenants
(
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    business_type INT,
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_business_type FOREIGN KEY (business_type)
        REFERENCES business_types (id)
);
