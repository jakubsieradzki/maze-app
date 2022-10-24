package org.challenge.maze.infrastructure.http.maze;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class MazeSolutionDto {
    public final List<String> path;

    @JsonCreator
    public MazeSolutionDto(List<String> path) {
        this.path = path;
    }
}
