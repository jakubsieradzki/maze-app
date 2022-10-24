package org.challenge.maze.domain.solver


import spock.lang.Specification

class DFSBacktrackingMazeSolverTest extends Specification {

    def "should solve"() {
        when:
        def path = new DFSBacktrackingMazeSolver().solve(testMaze.mazeRoute)

        then:
        println(path)
        path == testMaze.longest

        where:
        testMaze << TestMazes.all
    }

}
