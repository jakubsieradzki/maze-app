package org.challenge.maze.domain.solver


import spock.lang.Specification

class BFSMazeSolverTest extends Specification {

    def "should solve"() {
        when:
        def path = new BFSMazeSolver().solve(testMaze.mazeRoute)

        then:
        path == testMaze.shortest

        where:
        testMaze << TestMazes.all
    }
}
