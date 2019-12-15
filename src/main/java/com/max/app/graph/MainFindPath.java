package com.max.app.graph;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
        Location end = new Location(0, 5);

        Optional<List<Location>> path = findPath(map, start, end, new AstarTraversal());

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
     * Find a path in a 2D maze using DFS/BFS/A* approach.
     * <p>
     * N = rows * cols
     * time: O(N)
     * space: O(N)
     */
    static Optional<List<Location>> findPath(Maze map, Location start, Location end, TraversalStrategy traversal) {

        checkInMaze(map, start);
        checkInMaze(map, end);

        // check both locations 'start' and 'end' are available.
        if (!(map.isAvailable(start) && map.isAvailable(end))) {
            return Optional.empty();
        }

        Map<Location, Location> visited = traversal.doTraversal(map, start, end);

        if (visited.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildPath(start, end, visited));

    }

    private static Map<Location, Location> doBreadthTraversal(Maze map, Location start, Location end) {
        return new BfsTraversal().doTraversal(map, start, end);
    }

    private static Map<Location, Location> doDepthTraversal(Maze map, Location start, Location end) {
        return new DfsTraversal().doTraversal(map, start, end);
    }

    private static void checkInMaze(Maze map, Location location) {
        if (map.notInBoundary(location)) {
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


}
