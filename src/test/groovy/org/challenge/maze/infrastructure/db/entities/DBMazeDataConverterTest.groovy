package org.challenge.maze.infrastructure.db.entities


import org.challenge.maze.domain.model.CellType
import org.challenge.maze.domain.model.MazeData
import org.challenge.maze.infrastructure.db.exception.MazeDataCorruptedException
import spock.lang.Specification

import static org.challenge.maze.domain.model.CellType.PATH
import static org.challenge.maze.domain.model.CellType.WALL

class DBMazeDataConverterTest extends Specification {

    def "should convert maze data to string and back"() {
        given:
        def rows = mazeCell.size()
        def cols = mazeCell.first().size()
        def inputMaze = MazeData.of(mazeCell as CellType[][])

        when:
        def asString = DBMazeDataConverter.from(inputMaze)
        def convertedBack = DBMazeDataConverter.to(rows, cols, asString)

        then:
        convertedBack == inputMaze

        where:
        mazeCell << [
                [[WALL, PATH, PATH, WALL],
                 [WALL, WALL, PATH, WALL]],

                [[PATH, PATH, PATH, WALL],
                 [WALL, WALL, PATH, WALL],
                 [WALL, WALL, PATH, PATH],
                 [WALL, PATH, WALL, PATH],
                 [WALL, PATH, PATH, PATH],
                 [WALL, PATH, WALL, WALL]]
        ]
    }

    def "should throw exception when stored maze string has wrong length"() {
        when:
        DBMazeDataConverter.to(3, 3, incorrectMazeString)

        then:
        def e = thrown(MazeDataCorruptedException)
        println(e.getMessage())

        where:
        incorrectMazeString << [
                "000", // too short
                "1000001000" // to long
        ]
    }
}
