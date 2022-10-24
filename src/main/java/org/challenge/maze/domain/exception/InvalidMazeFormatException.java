package org.challenge.maze.domain.exception;

public class InvalidMazeFormatException extends RuntimeException {

    private InvalidMazeFormatException(String message) {
        super(message);
    }

    public static InvalidMazeFormatException invalidSize(String gridSize) {
        return new InvalidMazeFormatException(String.format(
                "Maze size '%s' has invalid format. The format is '{maze_width}x{maze_height}' e.g. 8x8", gridSize
        ));
    }

    public static InvalidMazeFormatException invalidCellFormat(String cellValue) {
        return new InvalidMazeFormatException(String.format(
                "Cell '%s' has invalid format. The format is '{upper_case_letter(s)}{positive_integer}' e.g. B4", cellValue
        ));
    }
}
