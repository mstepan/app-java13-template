package com.max.app.graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * Do A* traversal. Will return shortest path.
 */
final class AstarTraversal implements TraversalStrategy {

    @Override
    public Map<Location, Location> doTraversal(Maze map, Location start, Location end) {

        Map<Location, Location> visited = new HashMap<>();

        Queue<LocationWithDistance> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(new LocationWithDistance(start, 0, manhattanDistance(start, end)));
        visited.put(start, null);

        while (!priorityQueue.isEmpty()) {

            LocationWithDistance cur = priorityQueue.poll();

            if (end.equals(cur.location)) {
                return visited;
            }

            for (Location neigh : map.neighbour(cur.location)) {
                if (!visited.containsKey(neigh)) {

                    visited.put(neigh, cur.location);

                    priorityQueue.add(new LocationWithDistance(neigh, cur.realDistance + 1, manhattanDistance(neigh, end)));
                }
            }
        }

        return Collections.emptyMap();
    }

    private int manhattanDistance(Location start, Location end) {
        return Math.abs(end.row - start.row) + Math.abs(end.col - start.col);
    }

    private static final class LocationWithDistance implements Comparable<LocationWithDistance> {

        final Location location;
        final int realDistance;
        final int heuristicDistance;

        LocationWithDistance(Location location, int realDistance, int heuristicDistance) {
            this.location = location;
            this.realDistance = realDistance;
            this.heuristicDistance = heuristicDistance;
        }

        @Override
        public int compareTo(LocationWithDistance other) {
            return Integer.compare(realDistance + heuristicDistance, other.realDistance + other.heuristicDistance);
        }

        @Override
        public String toString() {
            return location + ", distance = " + realDistance + " + " + heuristicDistance;
        }
    }
}
