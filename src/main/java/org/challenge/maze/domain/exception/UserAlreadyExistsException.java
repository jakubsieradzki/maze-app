package org.challenge.maze.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("User with name '%s' already exists".formatted(username));
    }
}
