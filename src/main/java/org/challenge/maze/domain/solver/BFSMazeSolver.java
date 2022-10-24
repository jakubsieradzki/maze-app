package org.challenge.maze.domain.solver;

import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.MazeRoute;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class BFSMazeSolver implements MazeSolver {

    @Override
    public List<Cell> solve(MazeRoute maze) {
        Cell start = maze.entrance();
        Cell end = maze.exit();

        LinkedList<Cell> queue = new LinkedList<>();
        HashMap<Cell, Cell> visitedWithParent = new HashMap<>();

        queue.add(start);
        visitedWithParent.put(start, null);

        boolean solutionFound = false;
        while (!queue.isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                return List.of();
            }
            Cell current = queue.poll();
            if (current.equals(end)) {
                solutionFound = true;
                break;
            }

            for (Cell adjacent : maze.mazeData().adjacentPaths(current)) {
                if (!visitedWithParent.containsKey(adjacent)) {
                    queue.offer(adjacent);
                    visitedWithParent.put(adjacent, current);
                }
            }
        }

        LinkedList<Cell> path = new LinkedList<>();
        if (solutionFound) {
            Cell currentPathStep = end;
            path.add(currentPathStep);
            while (!currentPathStep.equals(start)) {
                Cell previousStep = visitedWithParent.get(currentPathStep);
                path.addFirst(previousStep);
                currentPathStep = previousStep;
            }
        }
        return path;
    }
}
