CREATE TABLE users (
   id BIGSERIAL,
   username VARCHAR(128) UNIQUE,
   password varchar(128)
)