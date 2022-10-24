package org.challenge.maze.domain.exception;

import org.challenge.maze.infrastructure.http.maze.SolverSteps;

public class NoSolverRegisteredException extends RuntimeException {

    public NoSolverRegisteredException(SolverSteps steps) {
        super("No solver is registered for: " + steps);
    }
}
