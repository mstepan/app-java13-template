package com.max.app.graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Do Depth First Search (DFS) traversal. Will return any random path (not necessary the shortest one).
 */
final class DfsTraversal implements TraversalStrategy {

    @Override
    public Map<Location, Location> doTraversal(Maze map, Location start, Location end) {
        Map<Location, Location> visited = new HashMap<>();

        Deque<Location> stack = new ArrayDeque<>();

        stack.push(start);
        visited.put(start, null);

        while (!stack.isEmpty()) {

            Location cur = stack.pop();

            if (end.equals(cur)) {
                return visited;
            }

            List<Location> neighbours = map.neighbour(cur);

            // do random shuffling
            Collections.shuffle(neighbours);

            for (Location neigh : neighbours) {
                if (!visited.containsKey(neigh)) {

                    visited.put(neigh, cur);

                    stack.push(cur);
                    stack.push(neigh);

                    break;
                }
            }
        }

        return Collections.emptyMap();
    }
}
