package org.challenge.maze.domain.solver

import org.challenge.maze.domain.model.MazeRoute
import org.challenge.maze.domain.exception.SolverFailedException
import spock.lang.Specification
import spock.lang.Timeout

import java.time.Duration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MazeSolverWithTimeoutTest extends Specification {
    private static ExecutorService testExecutor = Executors.newSingleThreadExecutor()

    def cleanupSpec() {
        testExecutor.shutdown()
    }

    @Timeout(5)
    def "should throw exception when taking too long"() {
        given:
        MazeSolver slowSolver = Stub(MazeSolver) {
            solve(_ as MazeRoute) >> {
                Thread.sleep(1000)
            }
        }
        def timeoutSolver = new MazeSolverWithTimeout(slowSolver, testExecutor, Duration.ofMillis(200))

        when:
        timeoutSolver.solve(Stub(MazeRoute))

        then:
        thrown(SolverFailedException)
    }
}
