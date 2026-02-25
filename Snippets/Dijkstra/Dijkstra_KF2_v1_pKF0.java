package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public final class Dijkstra {

    private static final Graph.Edge[] GRAPH = {
        new Graph.Edge("a", "b", 7),
        new Graph.Edge("a", "c", 9),
        new Graph.Edge("a", "f", 14),
        new Graph.Edge("b", "c", 10),
        new Graph.Edge("b", "d", 15),
        new Graph.Edge("c", "d", 11),
        new Graph.Edge("c", "f", 2),
        new Graph.Edge("d", "e", 6),
        new Graph.Edge("e", "f", 9),
    };

    private static final String START = "a";
    private static final String END = "e";

    private Dijkstra() {
        // Utility class
    }

    public static void main(String[] args) {
        Graph graph = new Graph(GRAPH);
        graph.dijkstra(START);
        graph.printPath(END);
    }
}

class Graph {

    private final Map<String, Vertex> vertices;

    public Graph(Edge[] edges) {
        vertices = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            vertices.computeIfAbsent(edge.v1, Vertex::new);
            vertices.computeIfAbsent(edge.v2, Vertex::new);
        }

        for (Edge edge : edges) {
            Vertex from = vertices.get(edge.v1);
            Vertex to = vertices.get(edge.v2);
            from.neighbours.put(to, edge.dist);
        }
    }

    public static class Edge {

        public final String v1;
        public final String v2;
        public final int dist;

        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        public int dist = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        public Vertex(String name) {
            this.name = name;
        }

        private void printPath() {
            if (this == this.previous) {
                System.out.printf("%s", this.name);
            } else if (this.previous == null) {
                System.out.printf("%s(unreached)", this.name);
            } else {
                this.previous.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.dist);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            int distanceComparison = Integer.compare(this.dist, other.dist);
            if (distanceComparison != 0) {
                return distanceComparison;
            }
            return this.name.compareTo(other.name);
        }

        @Override
        public String toString() {
            return "(" + name + ", " + dist + ")";
        }
    }

    public void dijkstra(String startName) {
        Vertex source = vertices.get(startName);
        if (source == null) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }

        NavigableSet<Vertex> queue = new TreeSet<>();

        for (Vertex vertex : vertices.values()) {
            vertex.previous = vertex == source ? source : null;
            vertex.dist = vertex == source ? 0 : Integer.MAX_VALUE;
            queue.add(vertex);
        }

        runDijkstra(queue);
    }

    private void runDijkstra(final NavigableSet<Vertex> queue) {
        while (!queue.isEmpty()) {
            Vertex current = queue.pollFirst();
            if (current.dist == Integer.MAX_VALUE) {
                break;
            }

            for (Map.Entry<Vertex, Integer> neighbourEntry : current.neighbours.entrySet()) {
                Vertex neighbour = neighbourEntry.getKey();
                int edgeWeight = neighbourEntry.getValue();
                int alternateDist = current.dist + edgeWeight;

                if (alternateDist < neighbour.dist) {
                    queue.remove(neighbour);
                    neighbour.dist = alternateDist;
                    neighbour.previous = current;
                    queue.add(neighbour);
                }
            }
        }
    }

    public void printPath(String endName) {
        Vertex endVertex = vertices.get(endName);
        if (endVertex == null) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endName);
            return;
        }

        endVertex.printPath();
        System.out.println();
    }

    public void printAllPaths() {
        for (Vertex vertex : vertices.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}