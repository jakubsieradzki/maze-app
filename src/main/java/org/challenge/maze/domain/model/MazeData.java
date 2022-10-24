package org.challenge.maze.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class MazeData {
    private static final int[][] DIRECTIONS = new int[][]{
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
    };

    private final MazeSize mazeSize;
    private final MazeCell[][] maze;

    public static MazeData of(CellType[][] cells) {
        int rows = cells.length;
        int columns = cells[0].length;
        MazeCell[][] mazeCells = new MazeCell[rows][columns];
        for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
            for (int colIdx = 0; colIdx < columns; colIdx++) {
                mazeCells[rowIdx][colIdx] = new MazeCell(Cell.of(rowIdx, colIdx), cells[rowIdx][colIdx]);
            }
        }
        return new MazeData(new MazeSize(rows, columns), mazeCells);
    }

    public MazeSize size() {
        return mazeSize;
    }

    public List<Cell> adjacentPaths(Cell current) {
        List<Cell> result = new ArrayList<>();
        for (int[] direction : DIRECTIONS) {
            int dirRow = current.row() + direction[0];
            int dirCol = current.column() + direction[1];
            if (contains(dirRow, dirCol) && cellIs(dirRow, dirCol, CellType.PATH)) {
                result.add(maze[dirRow][dirCol].cell());
            }
        }
        return result;
    }

    public List<MazeCell> allCells() {
        List<MazeCell> result = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < mazeSize.rows(); rowIdx++) {
            result.addAll(List.of(maze[rowIdx]));
        }
        return result;
    }

    public boolean contains(Cell cell) {
        return contains(cell.row(), cell.column());
    }

    public boolean contains(int row, int col) {
        return mazeSize.contains(row, col);
    }

    public boolean cellIs(Cell cell, CellType type) {
        return cellIs(cell.row(), cell.column(), type);
    }

    public boolean cellIs(int row, int col, CellType type) {
        return contains(row, col) && maze[row][col].type() == type;
    }
}
