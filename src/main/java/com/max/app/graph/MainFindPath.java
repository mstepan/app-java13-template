package com.max.app.graph;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public final class MainFindPath {

    private static final Logger LOG = LoggerFactory.getLogger(MainFindPath.class);

    public static void main(String[] args) throws Exception {

        int[][] data = {
                {1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1}
        };

        Maze map = new Maze(data);
        Location start = new Location(0, 0);
        Location end = new Location(4, 5);

        Optional<List<Location>> path = findPath(map, start, end);

        if (path.isPresent()) {
            LOG.info("Path: {}", path);
        }
        else {
            LOG.info("Can't find path");
        }

        LOG.info("Main done. java version {}", System.getProperty("java.version"));
    }


    /**
     * 19.1 Search a maze.
     * <p>
     * Find a path in a 2D maze using DFS approach.
     * <p>
     * N = rows * cols
     * time: O(N)
     * space: O(N)
     */
    static Optional<List<Location>> findPath(Maze map, Location start, Location end) {

        checkInMaze(map, start);
        checkInMaze(map, end);

        // check both locations 'start' and 'end' are available.
        if (!(map.isAvailable(start) && map.isAvailable(end))) {
            return Optional.empty();
        }

        Map<Location, Location> visited = new HashMap<>();

        Deque<Location> stack = new ArrayDeque<>();

        stack.push(start);
        visited.put(start, null);

        while (!stack.isEmpty()) {

            Location cur = stack.pop();

            if (end.equals(cur)) {
                return Optional.of(buildPath(start, end, visited));
            }

            for (Location neigh : map.neighbour(cur)) {
                if (!visited.containsKey(neigh)) {

                    visited.put(neigh, cur);

                    stack.push(cur);
                    stack.push(neigh);

                    break;
                }
            }
        }

        return Optional.empty();
    }

    private static void checkInMaze(Maze map, Location location) {
        if (map.notInBoundary(location.row, location.col)) {
            throw new IllegalArgumentException(location + " not within maze boundary");
        }
    }

    private static List<Location> buildPath(Location start, Location end, Map<Location, Location> locationWithParent) {

        List<Location> path = new ArrayList<>();

        Location cur = end;

        while (!start.equals(cur)) {

            path.add(cur);

            cur = locationWithParent.get(cur);
        }

        path.add(start);

        Collections.reverse(path);

        return path;
    }


    private static final class Location {
        final int row;
        final int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Location other = (Location) obj;

            return row == other.row && col == other.col;
        }

        @Override
        public int hashCode() {
            return 31 * row + col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    private static final class Maze {

        private final int rows;
        private final int cols;
        private final int[][] arr;

        private static final int[][] OFFSETS = {
                // {row, col}
                {1, 0}, // down
                {-1, 0}, // up
                {0, 1}, // right
                {0, -1} // left
        };

        Maze(int[][] originalArr) {
            Objects.requireNonNull(originalArr);
            checkSameNumOfColumns(originalArr);

            rows = originalArr.length;
            cols = originalArr[0].length;
            arr = new int[rows][];

            for (int i = 0; i < rows; ++i) {
                arr[i] = Arrays.copyOf(originalArr[i], cols);
            }
        }

        List<Location> neighbour(Location cur) {
            List<Location> adj = new ArrayList<>();

            int row = cur.row;
            int col = cur.col;

            for (int[] singleOffset : OFFSETS) {
                int adjRow = row + singleOffset[0];
                int adjCol = col + singleOffset[1];

                if (isAvailable(adjRow, adjCol)) {
                    adj.add(new Location(adjRow, adjCol));
                }
            }

            // random shuffling
            Collections.shuffle(adj);

            return adj;
        }

        private boolean isAvailable(int row, int col) {
            if (notInBoundary(row, col)) {
                return false;
            }

            return arr[row][col] == 1;
        }

        private boolean isAvailable(Location location) {
            return isAvailable(location.row, location.col);
        }

        private void checkSameNumOfColumns(int[][] arr) {

            int cols = arr[0].length;

            for (int i = 1; i < arr.length; ++i) {
                if (arr[i].length != cols) {
                    throw new IllegalArgumentException("Not all rows have same columns");
                }
            }
        }

        private boolean notInBoundary(int row, int col) {
            return row < 0 || row >= rows || col < 0 || col >= cols;
        }

    }


}
