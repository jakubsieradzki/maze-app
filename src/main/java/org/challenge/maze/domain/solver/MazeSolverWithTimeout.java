package org.challenge.maze.domain.solver;

import org.challenge.maze.domain.exception.SolverFailedException;
import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.MazeRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

class MazeSolverWithTimeout implements MazeSolver {
    private final Logger log = LoggerFactory.getLogger(MazeSolverWithTimeout.class);

    private final MazeSolver delegate;
    private final ExecutorService executorService;
    private final Duration timeout;

    MazeSolverWithTimeout(MazeSolver delegate, ExecutorService executorService, Duration timeout) {
        this.delegate = delegate;
        this.executorService = executorService;
        this.timeout = timeout;
    }

    @Override
    public List<Cell> solve(MazeRoute maze) {
        Future<List<Cell>> solverTask = executorService.submit((() -> delegate.solve(maze)));
        try {
            return solverTask.get(timeout.getSeconds(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw SolverFailedException.solverInterrupted(e);
        } catch (ExecutionException e) {
            throw SolverFailedException.unknownException(e);
        } catch (TimeoutException e) {
            boolean canceled = solverTask.cancel(true);
            log.info("Solving the maze {} with {} took more then {}, so the task was cancelled: {}", maze,
                    delegate.getClass().getSimpleName(), timeout, canceled);
            throw SolverFailedException.tookToLong(timeout);
        }
    }
}
