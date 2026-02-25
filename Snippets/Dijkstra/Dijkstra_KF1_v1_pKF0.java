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

    private Class1() {
    }

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

        Edge(String source, String destination, int weight) {
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

        Vertex(String name) {
            this.name = name;
        }

        private void printPath() {
            if (this == this.previous) {
                System.out.printf("%s", this.name);
            } else if (this.previous == null) {
                System.out.printf("%s(unreached)", this.name);
            } else {
                this.previous.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.distance);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            if (distance == other.distance) {
                return name.compareTo(other.name);
            }
            return Integer.compare(distance, other.distance);
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

    Class2(Edge[] edges) {
        vertices = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            vertices.computeIfAbsent(edge.source, Vertex::new);
            vertices.computeIfAbsent(edge.destination, Vertex::new);
        }

        for (Edge edge : edges) {
            vertices.get(edge.source).neighbours.put(vertices.get(edge.destination), edge.weight);
        }
    }

    public void computeShortestPaths(String startName) {
        if (!vertices.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }

        final Vertex source = vertices.get(startName);
        NavigableSet<Vertex> queue = new TreeSet<>();

        for (Vertex vertex : vertices.values()) {
            vertex.previous = vertex == source ? source : null;
            vertex.distance = vertex == source ? 0 : Integer.MAX_VALUE;
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
                int alternateDist = current.distance + neighbourEntry.getValue();

                if (alternateDist < neighbour.distance) {
                    queue.remove(neighbour);
                    neighbour.distance = alternateDist;
                    neighbour.previous = current;
                    queue.add(neighbour);
                }
            }
        }
    }

    public void printPath(String endName) {
        if (!vertices.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endName);
            return;
        }

        vertices.get(endName).printPath();
        System.out.println();
    }

    public void printAllPaths() {
        for (Vertex vertex : vertices.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}