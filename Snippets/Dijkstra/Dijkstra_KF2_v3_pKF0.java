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

    private static final String START_VERTEX = "a";
    private static final String END_VERTEX = "e";

    private Dijkstra() {
        // Utility class
    }

    public static void main(String[] args) {
        Graph graph = new Graph(GRAPH);
        graph.dijkstra(START_VERTEX);
        graph.printPath(END_VERTEX);
    }
}

class Graph {

    private final Map<String, Vertex> vertices;

    public Graph(Edge[] edges) {
        this.vertices = new HashMap<>(edges.length);
        initializeVertices(edges);
        initializeEdges(edges);
    }

    private void initializeVertices(Edge[] edges) {
        for (Edge edge : edges) {
            vertices.computeIfAbsent(edge.source, Vertex::new);
            vertices.computeIfAbsent(edge.target, Vertex::new);
        }
    }

    private void initializeEdges(Edge[] edges) {
        for (Edge edge : edges) {
            Vertex from = vertices.get(edge.source);
            Vertex to = vertices.get(edge.target);
            from.neighbours.put(to, edge.weight);
        }
    }

    public static class Edge {

        public final String source;
        public final String target;
        public final int weight;

        public Edge(String source, String target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
    }

    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        public int distance = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        public Vertex(String name) {
            this.name = name;
        }

        private void printPath() {
            if (this == this.previous) {
                System.out.printf("%s", this.name);
                return;
            }

            if (this.previous == null) {
                System.out.printf("%s(unreached)", this.name);
                return;
            }

            this.previous.printPath();
            System.out.printf(" -> %s(%d)", this.name, this.distance);
        }

        @Override
        public int compareTo(Vertex other) {
            int distanceComparison = Integer.compare(this.distance, other.distance);
            if (distanceComparison != 0) {
                return distanceComparison;
            }
            return this.name.compareTo(other.name);
        }

        @Override
        public String toString() {
            return "(" + name + ", " + distance + ")";
        }
    }

    public void dijkstra(String startName) {
        Vertex source = vertices.get(startName);
        if (source == null) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }

        NavigableSet<Vertex> queue = new TreeSet<>();
        initializeSingleSource(source, queue);
        runDijkstra(queue);
    }

    private void initializeSingleSource(Vertex source, NavigableSet<Vertex> queue) {
        for (Vertex vertex : vertices.values()) {
            if (vertex == source) {
                vertex.previous = source;
                vertex.distance = 0;
            } else {
                vertex.previous = null;
                vertex.distance = Integer.MAX_VALUE;
            }
            queue.add(vertex);
        }
    }

    private void runDijkstra(final NavigableSet<Vertex> queue) {
        while (!queue.isEmpty()) {
            Vertex current = queue.pollFirst();
            if (current.distance == Integer.MAX_VALUE) {
                break;
            }
            relaxNeighbours(current, queue);
        }
    }

    private void relaxNeighbours(Vertex current, NavigableSet<Vertex> queue) {
        for (Map.Entry<Vertex, Integer> neighbourEntry : current.neighbours.entrySet()) {
            Vertex neighbour = neighbourEntry.getKey();
            int edgeWeight = neighbourEntry.getValue();
            int alternateDistance = current.distance + edgeWeight;

            if (alternateDistance < neighbour.distance) {
                queue.remove(neighbour);
                neighbour.distance = alternateDistance;
                neighbour.previous = current;
                queue.add(neighbour);
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