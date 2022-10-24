package org.challenge.maze.domain;

import org.challenge.maze.domain.exception.InvalidMazeDataException;
import org.challenge.maze.domain.model.*;
import org.challenge.maze.domain.solver.MazeTask;
import org.challenge.maze.infrastructure.http.maze.MazeDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class MazeFactory {
    private final int maxRows;
    private final int maxCols;

    public MazeFactory(@Value("${app.maze.max-size.rows}") int maxRows,
                       @Value("${app.maze.max-size.cols}") int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
    }

    public MazeRoute createFrom(MazeDataDto mazeDataDto) {
        MazeData mazeData = createMazeData(mazeDataDto);
        Cell entrance = createEntrance(mazeDataDto, mazeData);
        Cell exit = createExit(mazeData, entrance);

        return new MazeRoute(mazeData, entrance, exit);
    }

    private MazeData createMazeData(MazeDataDto data) {
        MazeSize mazeSize = MazeEncoder.decodeSize(data.gridSize);
        if (mazeSize.rows() > maxRows || mazeSize.columns() > maxCols) {
            throw InvalidMazeDataException.mazeTooBig(mazeSize, maxRows, maxCols);
        }

        List<Cell> walls = parseWalls(data.walls, mazeSize);
        CellType[][] mazeCells = new CellType[mazeSize.rows()][mazeSize.columns()];
        for (int rowIdx = 0; rowIdx < mazeSize.rows(); rowIdx++) {
            for (int colIdx = 0; colIdx < mazeSize.columns(); colIdx++) {
                mazeCells[rowIdx][colIdx] = CellType.PATH;
            }
        }
        walls.forEach(wallCell -> mazeCells[wallCell.row()][wallCell.column()] = CellType.WALL);

        return MazeData.of(mazeCells);
    }

    private List<Cell> parseWalls(List<String> walls, MazeSize mazeSize) {
        return walls.stream()
                .distinct()
                .map(wall -> {
                    Cell wallCell = MazeEncoder.decodeCell(wall);
                    if (!mazeSize.contains(wallCell)) {
                        throw InvalidMazeDataException.wallOutsideOfMaze(wall, mazeSize);
                    }
                    return wallCell;
                })
                .toList();
    }

    private Cell createEntrance(MazeDataDto inputData, MazeData mazeData) {
        Cell entrance = MazeEncoder.decodeCell(inputData.entrance);
        if (!mazeData.contains(entrance) || mazeData.cellIs(entrance, CellType.WALL)) {
            throw InvalidMazeDataException.invalidEntrancePoint(entrance, mazeData.size());
        }
        return entrance;
    }

    private Cell createExit(MazeData mazeData, Cell entrance) {
        List<Cell> visitedBottomCellsWithoutEntrance = MazeTask.BFS(mazeData, entrance)
                .stream()
                .filter(cell -> isBottomCell(cell, mazeData))
                .filter(cell -> !cell.equals(entrance))
                .toList();

        if (visitedBottomCellsWithoutEntrance.size() == 0 && !isBottomCell(entrance, mazeData)) {
            throw InvalidMazeDataException.noExitReachableFromEntrance();
        } else if (visitedBottomCellsWithoutEntrance.size() > 1) {
            throw InvalidMazeDataException.moreThanOneExitFound();
        }

        return visitedBottomCellsWithoutEntrance.size() == 0 ?
                entrance : // exit is the same as entrance
                visitedBottomCellsWithoutEntrance.get(0);
    }

    private boolean isBottomCell(Cell cell, MazeData mazeData) {
        return cell.row() == mazeData.size().rows() - 1;
    }

}
