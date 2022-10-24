package org.challenge.maze.domain

import org.challenge.maze.domain.exception.InvalidMazeDataException
import org.challenge.maze.domain.model.Cell
import org.challenge.maze.infrastructure.http.maze.MazeDataDto
import spock.lang.Specification

class MazeFactoryTest extends Specification {

    private static final int maxRows = 20
    private static final int maxCols = 20
    private final MazeFactory mazeFactory = new MazeFactory(maxRows, maxCols)

    def "should check if entrance is within maze dimensions"() {
        given:
        def invalidMazeDataDto = new MazeDataDto("4x4", entrance, ["B1", "B2", "B3", "B4"])

        when:
        mazeFactory.createFrom(invalidMazeDataDto)

        then:
        def ex = thrown(InvalidMazeDataException)
        println(ex.getMessage())

        where:
        entrance << ["E1", "A5", "D5"]
    }

    def "should check if walls are within maze dimensions"() {
        given:
        def invalidMazeDataDto = new MazeDataDto("4x4", "A1", walls)

        when:
        mazeFactory.createFrom(invalidMazeDataDto)

        then:
        def ex = thrown(InvalidMazeDataException)
        println(ex.getMessage())

        where:
        walls << [["B1", "B2", "B3", "B5"],
                  ["B1", "B2", "A5", "B5", "E1"]]
    }

    def "should check if there is only one exit"() {
        given:
        def invalidMazeDataDto = new MazeDataDto("4x4", "A1", walls)

        when:
        mazeFactory.createFrom(invalidMazeDataDto)

        then:
        thrown(InvalidMazeDataException)

        where:
        walls << [
                ["B1", "B2", "B3", "B4", "A4"], // no exit
                ["B2", "C2", "C3", "A4", "C4"]  // two 2 exits
        ]
    }

    def "should create a maze with valid entrance at the bottom"() {
        given:
        def mazeWithEntranceAtTheBottom = new MazeDataDto("4x5", "C5", ["A5", "B2", "B3", "B5", "C2", "C3"])

        when:
        def mazeRoute = mazeFactory.createFrom(mazeWithEntranceAtTheBottom)

        then:
        mazeRoute.entrance() == Cell.of(4, 2)
        mazeRoute.exit() == Cell.of(4, 3)
    }

    def "should create a maze with entrance where the exit is"() {
        given:
        def mazeWithEntranceAtTheBottom = new MazeDataDto("4x5", "D5", ["A5", "B2", "B3", "B5", "C2", "C3", "C5"])

        when:
        def mazeRoute = mazeFactory.createFrom(mazeWithEntranceAtTheBottom)

        then:
        mazeRoute.entrance() == Cell.of(4, 3)
        mazeRoute.exit() == mazeRoute.entrance()
    }

    def "should ignore duplicated walls"() {
        given:
        def mazeWithEntranceAtTheBottom = new MazeDataDto("4x5", "A1",
                ["A5", "B2", "B3", "B5", "C5", "C2", "C3", "C3", "C3"])

        when:
        def mazeRoute = mazeFactory.createFrom(mazeWithEntranceAtTheBottom)

        then:
        mazeRoute.entrance() == Cell.of(0, 0)
        mazeRoute.exit() == Cell.of(4, 3)
    }

    def "should throw exception on too big maze"() {
        given:
        def invalidMazeDataDto = new MazeDataDto(tooBigGridSize, "A1", ["A5", "B2", "B3", "B5", "C2", "C3", "C5"])

        when:
        mazeFactory.createFrom(invalidMazeDataDto)

        then:
        thrown(InvalidMazeDataException)

        where:
        tooBigGridSize << [
                "${maxCols + 1}x${maxRows}",
                "${maxCols}x${maxRows + 1}"
        ]
    }
}
