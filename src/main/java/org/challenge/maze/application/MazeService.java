package org.challenge.maze.application;

import lombok.extern.slf4j.Slf4j;
import org.challenge.maze.domain.*;
import org.challenge.maze.domain.exception.MazeNotFoundException;
import org.challenge.maze.domain.exception.NoSolverRegisteredException;
import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.Maze;
import org.challenge.maze.domain.model.MazeRoute;
import org.challenge.maze.domain.solver.MazeSolver;
import org.challenge.maze.domain.solver.MazeSolvers;
import org.challenge.maze.infrastructure.http.maze.MazeDataDto;
import org.challenge.maze.infrastructure.http.maze.SolverSteps;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MazeService {
    private final MazeFactory mazeFactory;
    private final MazeRepository mazeRepository;
    private final MazeSolvers mazeSolvers;

    public MazeService(MazeRepository mazeJpaRepository, MazeFactory mazeFactory, MazeSolvers mazeSolvers) {
        this.mazeRepository = mazeJpaRepository;
        this.mazeFactory = mazeFactory;
        this.mazeSolvers = mazeSolvers;
    }

    public Maze addForUser(MazeDataDto data, String userId) {
        log.info("Creating a maze for user '{}' from: {}", userId, data);
        MazeRoute mazeRoute = mazeFactory.createFrom(data);
        return mazeRepository.saveForUser(mazeRoute, userId);
    }

    public List<Maze> findUserMazes(String userId) {
        log.info("Fetching user '{}' mazes", userId);
        return mazeRepository.findUserMazes(userId);
    }

    public List<Cell> solveMaze(long mazeId, SolverSteps steps) {
        log.info("Solving maze with id '{}' with steps: {}", mazeId, steps);
        MazeRoute mazeRoute = mazeRepository.find(mazeId)
                .orElseThrow(() -> new MazeNotFoundException(mazeId))
                .mazeRoute();

        MazeSolver solver = mazeSolvers.getFor(steps)
                .orElseThrow(() -> new NoSolverRegisteredException(steps));
        log.info("Using solver: {}", solver.getClass().getSimpleName());

        return solver.solve(mazeRoute);
    }
}
