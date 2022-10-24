package org.challenge.maze.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
@EqualsAndHashCode
public class Cell {
    private final int row;
    private final int column;

    public static Cell of(int row, int col) {
        return new Cell(row, col);
    }

    @Override
    public String toString() {
        return "(%d,%d)".formatted(row, column);
    }
}
