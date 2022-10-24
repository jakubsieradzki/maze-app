package org.challenge.maze.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public class MazeRoute {
    private final MazeData mazeData;
    private final Cell entrance;
    private final Cell exit;
}
