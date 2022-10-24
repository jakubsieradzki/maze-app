package org.challenge.maze.infrastructure.http.maze;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.challenge.maze.domain.model.MazeCell;
import org.challenge.maze.domain.MazeEncoder;
import org.challenge.maze.domain.model.MazeRoute;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ToString
public class MazeDataDto {
    @NotBlank(message = "Grid size cannot be blank")
    public final String gridSize;
    @NotBlank(message = "Entrance cell cannot be blank")
    public final String entrance;
    @NotEmpty(message = "Walls list cannot be empty")
    public final List<String> walls;

    public static MazeDataDto from(MazeRoute mazeRoute) {
        String gridSize = MazeEncoder.encodeSize(mazeRoute.mazeData().size());
        String entrance = MazeEncoder.encodeCell(mazeRoute.entrance());
        List<String> walls = new ArrayList<>();
        for (MazeCell mazeCell : mazeRoute.mazeData().allCells()) {
            if (mazeCell.isWall()) {
                walls.add(MazeEncoder.encodeCell(mazeCell.cell()));
            }
        }
        return new MazeDataDto(gridSize, entrance, walls);
    }
}
