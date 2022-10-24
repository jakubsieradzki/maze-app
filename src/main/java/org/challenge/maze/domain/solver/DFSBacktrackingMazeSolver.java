package org.challenge.maze.domain.solver;

import org.challenge.maze.domain.model.Cell;
import org.challenge.maze.domain.model.MazeData;
import org.challenge.maze.domain.model.MazeRoute;

import java.util.*;

class DFSBacktrackingMazeSolver implements MazeSolver {

    @Override
    public List<Cell> solve(MazeRoute mazeData) {
        LinkedList<Cell> currentPath = new LinkedList<>();
        List<Cell> longestPath = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();

        findPath(mazeData.mazeData(), mazeData.entrance(), mazeData.exit(), currentPath, longestPath, visited);

        return longestPath;
    }

    private void findPath(MazeData mazeData, Cell currentNode, Cell end, LinkedList<Cell> currentPath, List<Cell> longestPath, Set<Cell> visited) {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        if (currentNode.equals(end)) {
            currentPath.add(currentNode);
            if (currentPath.size() > longestPath.size()) {
                updateLongestPath(longestPath, currentPath);
            }
            currentPath.removeLast();
            return;
        }

        visited.add(currentNode);
        currentPath.add(currentNode);

        for (Cell nextCell : mazeData.adjacentPaths(currentNode)) {
            if (!visited.contains(nextCell)) {
                findPath(mazeData, nextCell, end, currentPath, longestPath, visited);
            }
        }

        visited.remove(currentNode);
        currentPath.removeLast();
    }

    private void updateLongestPath(List<Cell> longestPath, LinkedList<Cell> currentPath) {
        longestPath.clear();
        longestPath.addAll(currentPath);
    }
}
