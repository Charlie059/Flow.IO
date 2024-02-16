CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT PRIMARY KEY,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    tenant      BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (email),
    CONSTRAINT fk_tenant FOREIGN KEY (tenant)
        REFERENCES tenants(id)
);
