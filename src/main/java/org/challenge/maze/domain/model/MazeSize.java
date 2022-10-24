package org.challenge.maze.domain.model;

import java.util.Objects;

public class MazeSize {
    private final int rows;
    private final int columns;

    public MazeSize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public boolean contains(Cell cell) {
        return contains(cell.row(), cell.column());
    }

    public boolean contains(int row, int col) {
        return row >= 0 && row < rows
                && col >= 0 && col < columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeSize mazeSize = (MazeSize) o;
        return rows == mazeSize.rows && columns == mazeSize.columns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, columns);
    }

    @Override
    public String toString() {
        return "MazeSize{" +
                "rows=" + rows +
                ", columns=" + columns +
                '}';
    }
}
