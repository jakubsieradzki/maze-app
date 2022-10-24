package org.challenge.maze.domain.exception;

public class MazeNotFoundException extends RuntimeException {
    public MazeNotFoundException(long mazeId) {
        super("Did not found maze with id: " + mazeId);
    }
}
