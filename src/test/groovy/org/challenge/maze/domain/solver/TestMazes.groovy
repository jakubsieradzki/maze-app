package org.challenge.maze.domain.solver

import org.challenge.maze.domain.model.Cell
import org.challenge.maze.domain.model.CellType
import org.challenge.maze.domain.model.MazeData
import org.challenge.maze.domain.model.MazeRoute

import static org.challenge.maze.domain.model.Cell.of
import static org.challenge.maze.domain.model.CellType.PATH
import static org.challenge.maze.domain.model.CellType.WALL

class TestMazes {
    static final TestMaze simple = new TestMaze(
            new MazeRoute(MazeData.of([[PATH, PATH, WALL],
                                       [WALL, PATH, WALL],
                                       [PATH, PATH, WALL],
                                       [PATH, WALL, WALL],
                                       [PATH, WALL, PATH],
                                       [PATH, WALL, PATH],
                                       [PATH, WALL, PATH]] as CellType[][]),
                    of(0, 0), of(6, 0)),
            [of(0, 0), of(0, 1), of(1, 1), of(2, 1), of(2, 0), of(3, 0), of(4, 0), of(5, 0), of(6, 0)],
            [of(0, 0), of(0, 1), of(1, 1), of(2, 1), of(2, 0), of(3, 0), of(4, 0), of(5, 0), of(6, 0)]
    )

    static final TestMaze twoPaths = new TestMaze(
            new MazeRoute(MazeData.of([[PATH, PATH, PATH, PATH, PATH, WALL],
                                       [PATH, WALL, WALL, WALL, PATH, PATH],
                                       [PATH, PATH, PATH, WALL, WALL, PATH],
                                       [PATH, PATH, PATH, WALL, PATH, PATH],
                                       [PATH, WALL, WALL, WALL, PATH, WALL],
                                       [PATH, PATH, PATH, PATH, PATH, WALL]] as CellType[][]),
                    of(0, 0), of(5, 4)),
            [of(0, 0), of(1, 0), of(2, 0), of(3, 0), of(4, 0), of(5, 0), of(5, 1), of(5, 2), of(5, 3), of(5, 4)],
            [of(0, 0), of(1, 0), of(2, 0), of(2, 1), of(2, 2), of(3, 2), of(3, 1), of(3, 0), of(4, 0), of(5, 0), of(5, 1), of(5, 2), of(5, 3), of(5, 4)]
    )

    static final TestMaze empty = new TestMaze(
            new MazeRoute(MazeData.of([[PATH, PATH, PATH, PATH, PATH],
                                       [PATH, PATH, PATH, PATH, PATH],
                                       [PATH, PATH, PATH, PATH, PATH],
                                       [PATH, PATH, PATH, PATH, PATH],
                                       [PATH, PATH, PATH, PATH, PATH]] as CellType[][]),
                    of(0, 0), of(4, 4)),
            [of(0, 0), of(1, 0), of(2, 0), of(3, 0), of(4, 0), of(4, 1), of(4, 2), of(4, 3), of(4, 4)],
            [of(0, 0), of(1, 0), of(2, 0), of(3, 0), of(4, 0),
             of(4, 1), of(3, 1), of(2, 1), of(1, 1), of(0, 1),
             of(0, 2), of(1, 2), of(2, 2), of(3, 2), of(4, 2),
             of(4, 3), of(3, 3), of(2, 3), of(1, 3), of(0, 3),
             of(0, 4), of(1, 4), of(2, 4), of(3, 4), of(4, 4)]
    )

    static final TestMaze bigger = new TestMaze(
            new MazeRoute(MazeData.of([
                    [PATH, WALL, PATH, PATH, PATH, PATH, WALL, PATH, PATH, PATH],
                    [PATH, WALL, PATH, WALL, PATH, PATH, PATH, WALL, PATH, PATH],
                    [PATH, PATH, PATH, WALL, PATH, PATH, WALL, PATH, WALL, PATH],
                    [WALL, WALL, WALL, WALL, PATH, WALL, WALL, PATH, WALL, WALL],
                    [PATH, WALL, WALL, WALL, PATH, PATH, PATH, PATH, PATH, PATH],
                    [PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, WALL],
                    [PATH, WALL, WALL, WALL, PATH, WALL, WALL, PATH, WALL, PATH],
                    [PATH, WALL, PATH, PATH, PATH, PATH, WALL, WALL, PATH, PATH],
                    [PATH, PATH, WALL, WALL, PATH, WALL, WALL, WALL, WALL, PATH],
                    [PATH, WALL, PATH, PATH, PATH, PATH, WALL, PATH, WALL, WALL]

            ] as CellType[][]),
                    of(0, 0), of(5, 7)),
            [of(0, 0), of(1, 0), of(2, 0), of(2, 1), of(2, 2), of(1, 2), of(0, 2), of(0, 3),
             of(0, 4), of(1, 4), of(2, 4), of(3, 4), of(4, 4), of(5, 4), of(5, 5), of(5, 6), of(5, 7)],
            [of(0, 0), of(1, 0), of(2, 0), of(2, 1), of(2, 2), of(1, 2), of(0, 2), of(0, 3), of(0, 4), of(1, 4),
             of(1, 5), of(2, 5), of(2, 4), of(3, 4), of(4, 4), of(5, 4), of(5, 5), of(4, 5), of(4, 6), of(4, 7),
             of(4, 8), of(5, 8), of(5, 7)]
    )

    static final all = [simple, twoPaths, empty, bigger]

    static class TestMaze {
        public MazeRoute mazeRoute
        public List<Cell> shortest
        public List<Cell> longest

        TestMaze(MazeRoute mazeRoute, List<Cell> shortest, List<Cell> longest) {
            this.mazeRoute = mazeRoute
            this.shortest = shortest
            this.longest = longest
        }
    }


}
