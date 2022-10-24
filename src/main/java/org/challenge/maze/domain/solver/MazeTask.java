package org.challenge.maze.domain.solver;

import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.MazeData;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MazeTask {

    public static Collection<Cell> BFS(MazeData mazeData, Cell start) {
        LinkedList<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            for (Cell adjacent : mazeData.adjacentPaths(current)) {
                if (!visited.contains(adjacent)) {
                    queue.offer(adjacent);
                    visited.add(adjacent);
                }
            }
        }

        return visited;
    }
}
