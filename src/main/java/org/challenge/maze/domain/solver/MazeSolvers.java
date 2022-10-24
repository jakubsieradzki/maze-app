package org.challenge.maze.domain.solver;

import org.challenge.maze.infrastructure.http.maze.SolverSteps;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class MazeSolvers {
    private final Map<SolverSteps, MazeSolver> solvers;

    public MazeSolvers(EnumMap<SolverSteps, MazeSolver> solvers) {
        this.solvers = solvers;
    }

    public Optional<MazeSolver> getFor(SolverSteps steps) {
        return Optional.ofNullable(solvers.get(steps));
    }
}
