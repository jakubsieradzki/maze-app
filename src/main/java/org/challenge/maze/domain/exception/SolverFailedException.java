package org.challenge.maze.domain.exception;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class SolverFailedException extends RuntimeException {
    private SolverFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    private SolverFailedException(String message) {
        super(message);
    }

    public static RuntimeException solverInterrupted(InterruptedException cause) {
        return new SolverFailedException("Solver was rudely interrupted", cause);
    }

    public static SolverFailedException unknownException(ExecutionException cause) {
        return new SolverFailedException("Solver failed with:", cause);
    }

    public static SolverFailedException tookToLong(Duration timeout) {
        return new SolverFailedException("I tried to solve it in given time %s, but failed :(".formatted(timeout));
    }
}
