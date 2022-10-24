package org.challenge.maze.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@ToString
@Getter
@Accessors(fluent = true)
public class Maze {
    private final long id;
    private final MazeRoute mazeRoute;
}
