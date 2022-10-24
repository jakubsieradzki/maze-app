package org.challenge.maze.infrastructure.http.maze;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.challenge.maze.domain.model.Maze;

@AllArgsConstructor
@ToString
public class MazeDto {
    public final long id;
    public final MazeDataDto data;

    public static MazeDto from(Maze createdMaze) {
        return new MazeDto(
                createdMaze.id(),
                MazeDataDto.from(createdMaze.mazeRoute())
        );
    }
}
