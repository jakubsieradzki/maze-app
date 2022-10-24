package org.challenge.maze.infrastructure.db.exception;

public class MazeDataCorruptedException extends RuntimeException {
    private MazeDataCorruptedException(String message) {
        super(message);
    }

    public static MazeDataCorruptedException incorrectLength(int rows, int columns, String mazeDataString) {
        return new MazeDataCorruptedException(
                "Stored maze '%s' has incorrect length for size of %d x %d = %d != %d".formatted(
                        mazeDataString, rows, columns, rows * columns, mazeDataString.length()
                )
        );
    }
}
