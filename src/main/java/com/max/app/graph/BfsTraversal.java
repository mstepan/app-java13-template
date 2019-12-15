package com.max.app.graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


/**
 * Do Breadth First Search traversal. Will return shortest path.
 */
final class BfsTraversal implements TraversalStrategy {

    @Override
    public Map<Location, Location> doTraversal(Maze map, Location start, Location end) {

        Map<Location, Location> visited = new HashMap<>();

        Deque<Location> queue = new ArrayDeque<>();

        queue.add(start);
        visited.put(start, null);

        while (!queue.isEmpty()) {

            Location cur = queue.poll();

            if (end.equals(cur)) {
                return visited;
            }

            for (Location neigh : map.neighbour(cur)) {
                if (!visited.containsKey(neigh)) {

                    visited.put(neigh, cur);
                    queue.add(neigh);
                }
            }
        }

        return Collections.emptyMap();
    }
}
