package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Example usage of Dijkstra's shortest path algorithm.
 */
public final class Class1 {

    private static final Class2.Edge[] EDGES = {
        new Class2.Edge("a", "b", 7),
        new Class2.Edge("a", "c", 9),
        new Class2.Edge("a", "f", 14),
        new Class2.Edge("b", "c", 10),
        new Class2.Edge("b", "d", 15),
        new Class2.Edge("c", "d", 11),
        new Class2.Edge("c", "f", 2),
        new Class2.Edge("d", "e", 6),
        new Class2.Edge("e", "f", 9),
    };

    private static final String START_VERTEX = "a";
    private static final String END_VERTEX = "e";

    private Class1() {
        // Utility class
    }

    public static void main(String[] args) {
        Class2 graph = new Class2(EDGES);
        graph.computeShortestPaths(START_VERTEX);
        graph.printPath(END_VERTEX);
    }
}

class Class2 {

    private final Map<String, Vertex> vertices;

    public static class Edge {

        public final String source;
        public final String destination;
        public final int weight;

        public Edge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
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
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Vertex)) {
                return false;
            }
            Vertex other = (Vertex) obj;
            return distance == other.distance
                && Objects.equals(name, other.name)
                && Objects.equals(previous, other.previous)
                && Objects.equals(neighbours, other.neighbours);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, distance, previous, neighbours);
        }

        @Override
        public String toString() {
            return "(" + name + ", " + distance + ")";
        }
    }

    public Class2(Edge[] edges) {
        this.vertices = new HashMap<>(edges.length);
        initializeVertices(edges);
        initializeEdges(edges);
    }

    private void initializeVertices(Edge[] edges) {
        for (Edge edge : edges) {
            vertices.computeIfAbsent(edge.source, Vertex::new);
            vertices.computeIfAbsent(edge.destination, Vertex::new);
        }
    }

    private void initializeEdges(Edge[] edges) {
        for (Edge edge : edges) {
            Vertex sourceVertex = vertices.get(edge.source);
            Vertex destinationVertex = vertices.get(edge.destination);
            sourceVertex.neighbours.put(destinationVertex, edge.weight);
        }
    }

    public void computeShortestPaths(String startName) {
        Vertex source = vertices.get(startName);
        if (source == null) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }

        NavigableSet<Vertex> queue = new TreeSet<>();
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

        computeShortestPaths(queue);
    }

    private void computeShortestPaths(final NavigableSet<Vertex> queue) {
        while (!queue.isEmpty()) {
            Vertex current = queue.pollFirst();
            if (current.distance == Integer.MAX_VALUE) {
                break;
            }

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