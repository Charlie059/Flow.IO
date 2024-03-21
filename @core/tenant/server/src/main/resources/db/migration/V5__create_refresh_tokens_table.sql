CREATE TABLE IF NOT EXISTS refresh_tokens
(
    id         BIGSERIAL PRIMARY KEY,
    token      TEXT        NOT NULL,
    type       VARCHAR(10) NOT NULL,
    user_id    BIGINT      NOT NULL,
    revoked    BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);
