package org.challenge.maze.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Accessors(fluent = true)
public class MazeCell {
    private final Cell cell;
    private final CellType type;

    public boolean isPath() {
        return type == CellType.PATH;
    }

    public boolean isWall() {
        return type == CellType.WALL;
    }
}
