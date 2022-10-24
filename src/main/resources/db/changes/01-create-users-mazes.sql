--liquibase formatted sql

--changeset me:1
CREATE TABLE users (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

--changeset me:2
CREATE TABLE mazes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    owner VARCHAR(255),
    columns INTEGER NOT NULL,
    rows INTEGER NOT NULL,
    entrance_row INTEGER NOT NULL,
    entrance_col INTEGER NOT NULL,
    exit_row INTEGER NOT NULL,
    exit_col INTEGER NOT NULL,
    maze_data VARCHAR(10000)
);
ALTER TABLE mazes
ADD FOREIGN KEY (owner) REFERENCES users(username);