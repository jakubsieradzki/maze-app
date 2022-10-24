package org.challenge.maze.domain.exception;

import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.MazeSize;

public class InvalidMazeDataException extends RuntimeException {
    private InvalidMazeDataException(String details) {
        super("Invalid maze data: " + details);
    }

    public static InvalidMazeDataException invalidEntrancePoint(Cell entrance, MazeSize size) {
        return new InvalidMazeDataException(
                String.format("Entrance '%s' is outside of maze of size or is on the wall. Size: %s", entrance, size)
        );
    }

    public static InvalidMazeDataException wallOutsideOfMaze(String wall, MazeSize mazeSize) {
        return new InvalidMazeDataException(
                String.format("Wall '%s' is outside of maze of size %s", wall, mazeSize)
        );
    }

    public static InvalidMazeDataException noExitReachableFromEntrance() {
        return new InvalidMazeDataException("No reachable exit was found");
    }

    public static InvalidMazeDataException moreThanOneExitFound() {
        return new InvalidMazeDataException("More than one exit was found reachable from the entrance");
    }

    public static InvalidMazeDataException mazeTooBig(MazeSize mazeSize, int maxRows, int maxCols) {
        return new InvalidMazeDataException(
                "Given maze size (%s) is too big. Current max sizes are rows: %d, columns: %s"
                        .formatted(mazeSize, maxRows, maxCols)
        );
    }
}
