package org.challenge.maze.infrastructure.db.entities;

import org.challenge.maze.domain.model.*;
import org.challenge.maze.infrastructure.db.exception.MazeDataCorruptedException;

public class DBMazeDataConverter {

    public static String from(MazeData mazeData) {
        return mazeData.allCells().stream()
                .map(Encoding::from)
                .reduce(new StringBuffer(), StringBuffer::append, StringBuffer::append)
                .toString();
    }

    public static MazeData to(int rows, int columns, String mazeDataString) {
        CellType[][] mazeCells = new CellType[rows][columns];
        if ((rows * columns) != mazeDataString.length()) {
            throw MazeDataCorruptedException.incorrectLength(rows, columns, mazeDataString);
        }

        char[] chars = mazeDataString.toCharArray();
        for (int idx = 0; idx < chars.length; ) {
            int rowIdx = idx / columns;
            for (int colIdx = 0; colIdx < columns; colIdx++) {
                char cellChar = chars[idx++];
                CellType type = fromDB(cellChar);
                mazeCells[rowIdx][colIdx] = type;
            }
        }

        return MazeData.of(mazeCells);
    }

    private static CellType fromDB(char cellChar) {
        if (cellChar == Encoding.WALL) {
            return CellType.WALL;
        } else {
            return CellType.PATH;
        }
    }

    private static class Encoding {
        private static final char PATH = '0';
        private static final char WALL = '1';

        public static char from(MazeCell mazeCell) {
            return mazeCell.isWall() ? WALL : PATH;
        }
    }
}
