package com.max.app.graph;

import java.util.Map;

public interface TraversalStrategy {

    Map<Location, Location> doTraversal(Maze map, Location start, Location end);

}
