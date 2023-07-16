CREATE TABLE IF NOT EXISTS users (
   id             BIGSERIAL,
   username       VARCHAR(128) UNIQUE,
   password       VARCHAR(128),
   is_disabled    BOOLEAN
);

CREATE INDEX IF NOT EXISTS users__username__idx
    ON users (username);

CREATE INDEX IF NOT EXISTS users__disabled_username__idx
    ON users (is_disabled, username);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT,
    role    VARCHAR(32)
);

CREATE UNIQUE INDEX IF NOT EXISTS user_roles__user_role__idx
    ON user_roles (user_id, role);

CREATE TABLE IF NOT EXISTS jwt_tokens (
    id            BIGSERIAL,
    jwt_token     VARCHAR(32),
    expiration_ts BIGINT,
    type          VARCHAR(16)
);

CREATE INDEX IF NOT EXISTS jwt_tokens__type_expiration_ts__idx
    ON jwt_tokens (type, expiration_ts);