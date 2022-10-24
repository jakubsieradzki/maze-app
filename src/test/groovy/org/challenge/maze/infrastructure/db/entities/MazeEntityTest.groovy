package org.challenge.maze.infrastructure.db.entities


import org.challenge.maze.domain.model.Cell
import org.challenge.maze.domain.model.CellType
import org.challenge.maze.domain.model.MazeData
import org.challenge.maze.domain.model.MazeRoute
import spock.lang.Specification

import static org.challenge.maze.domain.model.CellType.PATH
import static org.challenge.maze.domain.model.CellType.WALL

class MazeEntityTest extends Specification {

    def "should create entity from domain object"() {
        given:
        def mazeData = MazeData.of([
                [WALL, PATH, PATH, PATH],
                [PATH, PATH, PATH, WALL],
        ] as CellType[][])
        def mazeRoute = new MazeRoute(mazeData, Cell.of(0, 3), Cell.of(1, 0))

        when:
        def entity = MazeEntity.from(mazeRoute, "some-user")

        then:
        with(entity) {
            it.owner == "some-user"
            it.rows == mazeData.size().rows()
            it.columns == mazeData.size().columns()
            it.entranceRow == mazeRoute.entrance().row()
            it.entranceCol == mazeRoute.entrance().column()
            it.exitRow == mazeRoute.exit().row()
            it.exitCol == mazeRoute.exit().column()
            it.mazeData == "1000" +
                           "0001"
        }
    }
}
