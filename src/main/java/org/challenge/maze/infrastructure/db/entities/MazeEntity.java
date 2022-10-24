package org.challenge.maze.infrastructure.db.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.Maze;
import org.challenge.maze.domain.model.MazeData;
import org.challenge.maze.domain.model.MazeRoute;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "mazes")
public class MazeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String owner;
    @NotNull
    private Integer rows;
    @NotNull
    private Integer columns;
    @NotNull
    private Integer entranceRow;
    @NotNull
    private Integer entranceCol;
    @NotNull
    private Integer exitRow;
    @NotNull
    private Integer exitCol;

    @Column(length = 10_000)
    private String mazeData;

    public Maze toDomain() {
        MazeData mazeData = DBMazeDataConverter.to(rows, columns, this.mazeData);
        return new Maze(
                id,
                new MazeRoute(mazeData, Cell.of(entranceRow, entranceCol), Cell.of(exitRow, exitCol))
        );
    }

    public static MazeEntity from(MazeRoute mazeRoute, String username) {
        return new MazeEntity()
                .setOwner(username)
                .setRows(mazeRoute.mazeData().size().rows())
                .setColumns(mazeRoute.mazeData().size().columns())
                .setEntranceRow(mazeRoute.entrance().row())
                .setEntranceCol(mazeRoute.entrance().column())
                .setExitRow(mazeRoute.exit().row())
                .setExitCol(mazeRoute.exit().column())
                .setMazeData(DBMazeDataConverter.from(mazeRoute.mazeData()));
    }
}
