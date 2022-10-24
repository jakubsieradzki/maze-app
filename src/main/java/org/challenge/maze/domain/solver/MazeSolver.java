package org.challenge.maze.domain.solver;

import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.MazeRoute;

import java.util.List;

public interface MazeSolver {
    List<Cell> solve(MazeRoute maze);
}
