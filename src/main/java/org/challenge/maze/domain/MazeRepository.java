package org.challenge.maze.domain;

import org.challenge.maze.domain.model.Maze;
import org.challenge.maze.domain.model.MazeRoute;

import java.util.List;
import java.util.Optional;

public interface MazeRepository {
    Maze saveForUser(MazeRoute mazeRoute, String username);

    List<Maze> findUserMazes(String username);

    Optional<Maze> find(long mazeId);
}
