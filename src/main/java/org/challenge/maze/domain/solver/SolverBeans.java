package org.challenge.maze.domain.solver;

import org.challenge.maze.infrastructure.http.maze.SolverSteps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.EnumMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class SolverBeans {

    @Value("${app.solver.timeout}")
    private Duration timeout;

    @Value("${app.solver.threadCount}")
    private int solverThreads;

    @Bean
    public MazeSolvers wrappedInTimeoutSolvers(ExecutorService solverExecutorService) {
        EnumMap<SolverSteps, MazeSolver> solvers = new EnumMap<>(SolverSteps.class);
        solvers.put(SolverSteps.MIN, new MazeSolverWithTimeout(new BFSMazeSolver(), solverExecutorService, timeout));
        solvers.put(SolverSteps.MAX, new MazeSolverWithTimeout(new DFSBacktrackingMazeSolver(), solverExecutorService, timeout));

        return new MazeSolvers(solvers);
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService solverExecutorService() {
        return Executors.newFixedThreadPool(solverThreads);
    }
}
