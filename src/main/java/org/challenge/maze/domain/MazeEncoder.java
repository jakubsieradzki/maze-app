package org.challenge.maze.domain;

import org.challenge.maze.domain.exception.InvalidMazeFormatException;
import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.MazeSize;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MazeEncoder {
    private static final int LETTERS_LENGTH = 26;
    private static final Pattern CELL_PATTERN = Pattern.compile("^([A-Z]+)([1-9]\\d*)$");
    private static final String DELIMITER = "x";
    private final static Pattern SIZE_PATTERN = Pattern.compile("[1-9]\\d*" + DELIMITER + "[1-9]\\d*");

    public static Cell decodeCell(String value) {
        Matcher matcher = CELL_PATTERN.matcher(value);
        if (matcher.matches()) {
            int rowIdx = parseRowNumber(matcher.group(2))
                    .orElseThrow(() -> InvalidMazeFormatException.invalidCellFormat(value));
            int columnIdx = parseColumnLetter(matcher.group(1));
            return Cell.of(rowIdx, columnIdx);
        } else {
            throw InvalidMazeFormatException.invalidCellFormat(value);
        }
    }

    public static String encodeCell(Cell cell) {
        String row = encodeRow(cell);
        String col = encodeCol(cell.column());

        return col + row;
    }

    private static String encodeCol(int colIdx) {
        StringBuilder result = new StringBuilder();
        int value = colIdx + 1;
        while (value > 0) {
            int modulo = (value - 1) % LETTERS_LENGTH;
            result.insert(0, (char) ('A' + modulo));
            value = (value - modulo) / LETTERS_LENGTH;
        }

        return result.toString();
    }

    private static String encodeRow(Cell cell) {
        return String.valueOf(cell.row() + 1);
    }

    public static MazeSize decodeSize(String gridSize) {
        Matcher matcher = SIZE_PATTERN.matcher(gridSize);
        if (matcher.matches()) {
            String[] sizes = gridSize.split(DELIMITER);
            int columns = Integer.parseInt(sizes[0]);
            int rows = Integer.parseInt(sizes[1]);
            return new MazeSize(rows, columns);
        } else {
            throw InvalidMazeFormatException.invalidSize(gridSize);
        }
    }

    public static String encodeSize(MazeSize size) {
        return size.columns() + DELIMITER + size.rows();
    }

    private static Optional<Integer> parseRowNumber(String row) {
        int intValue = Integer.parseInt(row);
        if (intValue < 1) {
            return Optional.empty();
        }
        return Optional.of(intValue - 1);
    }

    private static int parseColumnLetter(String letters) {
        int result = 0;
        for (char letter : letters.toCharArray()) {
            int currentValue = (letter - 'A') + 1;
            result *= LETTERS_LENGTH;
            result += currentValue;
        }

        return result - 1;
    }
}
