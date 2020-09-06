package com.max.app.hackerank;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public final class MinPenalty {

    /*
    Expected output: 119
    Actual result: 127
     */
    private static final String FILE_PATH =
            "/Users/mstepan/repo/app-java13-template/src/main/java/com/max/app/failed-test-case.txt";

    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(Paths.get(FILE_PATH).toFile());

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        int[][] edges = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] edgesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int edgesItem = Integer.parseInt(edgesRowItems[j]);
                edges[i][j] = edgesItem;
            }
        }

        String[] AB = scanner.nextLine().split(" ");

        int A = Integer.parseInt(AB[0]);
        int B = Integer.parseInt(AB[1]);

        int result = beautifulPath(edges, A, B);

        System.out.println("Result: " + result);

        scanner.close();
    }

    private static final int MAX_EDGE_COST = 1024;

    // use slightly modified Dijkstra shortest path algorithm
    static int beautifulPath(int[][] edges, int src, int dest) {

        Graph graph = Graph.create(edges);

        CustomMinQueue minHeap = new CustomMinQueue();

        for (int singleVertex : graph.getVertexes()) {
            minHeap.add(new VertexAndPath(singleVertex, (singleVertex == src) ? 0 : MAX_EDGE_COST));
        }

        Set<Integer> visitedVertexes = new HashSet<>();

        while (!minHeap.isEmpty()) {

            VertexAndPath curSolution = minHeap.poll();
            visitedVertexes.add(curSolution.vertex);

            if (curSolution.vertex == dest) {
                return curSolution.cost;
            }

            for (Edge edge : graph.adjVertexes(curSolution.vertex)) {

                // skip already visited vertexes
                if (visitedVertexes.contains(edge.dest)) {
                    continue;
                }

                VertexAndPath vertexSolution = minHeap.get(edge.dest);

                // use bitwise OR as a cost function
                final int newCost = curSolution.cost | edge.cost;

                final int prevCost = minHeap.get(edge.dest).cost;

                if (newCost < prevCost) {
                    minHeap.changePriority(edge.dest, newCost);
                }

            }
        }

        return -1;
    }

    private static final class Edge {
        final int dest;
        int cost;

        public Edge(int vertex, int cost) {
            this.dest = vertex;
            this.cost = cost;
        }
    }

    private static final class Graph {

        private final Map<Integer, List<Edge>> edges = new HashMap<>();

        static Graph create(int[][] edges) {

            Graph graph = new Graph();

            for (int[] singleEdge : edges) {
                graph.addEdge(singleEdge[0], singleEdge[1], singleEdge[2]);
            }

            return graph;
        }

        List<Edge> adjVertexes(int curVertex) {
            return edges.get(curVertex);
        }

        Set<Integer> getVertexes() {
            return edges.keySet();
        }

        void addEdge(int src, int dest, int cost) {
            // skip self-edges
            if (src == dest) {
                return;
            }

            if (!edges.containsKey(src)) {
                edges.put(src, new ArrayList<>());
            }

            if (!edges.containsKey(dest)) {
                edges.put(dest, new ArrayList<>());
            }

            edges.get(src).add(new Edge(dest, cost));
            edges.get(dest).add(new Edge(src, cost));
        }
    }

    private static final class CustomMinQueue {

        private final PriorityQueue<VertexAndPath> minHeap = new PriorityQueue<>(VertexAndPath.COST_ASC);
        private final Map<Integer, VertexAndPath> map;

        CustomMinQueue() {
            map = new HashMap<>();
        }

        VertexAndPath get(int vertex) {
            assert vertex >= 1;
            return map.get(vertex);
        }

        boolean isEmpty() {
            return minHeap.isEmpty();
        }

        VertexAndPath poll() {
            return minHeap.poll();
        }

        void add(VertexAndPath vertexAndPath) {
            map.put(vertexAndPath.vertex, vertexAndPath);
            minHeap.add(vertexAndPath);
        }

        // change priority
        void changePriority(int vertexToSearch, int newCost) {
            if (minHeap.removeIf(vertexAndPath -> vertexAndPath.vertex == vertexToSearch)) {

                VertexAndPath curVertexAndPath = map.get(vertexToSearch);

                curVertexAndPath.cost = newCost;
                minHeap.add(curVertexAndPath);
            }
            else {
                throw new IllegalArgumentException("Can't find vertex: " + vertexToSearch);
            }
        }
    }

    private static final class VertexAndPath {

        private static final Comparator<VertexAndPath> COST_ASC = Comparator.comparing(VertexAndPath::getCost);

        final int vertex;
        int cost;

        VertexAndPath(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return "vertex: " + vertex + ", cost: " + cost;
        }
    }


}

