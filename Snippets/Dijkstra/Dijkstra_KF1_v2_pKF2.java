package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Demonstrates Dijkstra's shortest path algorithm on a small graph.
 */
public final class Class1 {

    private Class1() {}

    /**
     * Sample directed, weighted graph represented as an array of edges.
     * Each edge is defined by (source, target, weight).
     */
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

    /** All vertices in the graph, keyed by vertex name. */
    private final Map<String, Vertex> vertices;

    /**
     * Immutable edge definition.
     * Represents a directed edge from {@code source} to {@code target} with a given {@code weight}.
     */
    public static class Edge {
        public final String source;
        public final String target;
        public final int weight;

        Edge(String source, String target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
    }

    /**
     * Vertex in the graph, used by Dijkstra's algorithm.
     * Stores the current best-known distance from the source and a pointer to the previous vertex on that path.
     */
    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        public int distance = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        Vertex(String name) {
            this.name = name;
        }

        /**
         * Prints the path from the start vertex to this vertex.
         * The path is printed in order: start -> ... -> this, including cumulative distances.
         */
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
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Vertex other = (Vertex) obj;

            if (distance != other.distance) {
                return false;
            }
            if (name != null ? !name.equals(other.name) : other.name != null) {
                return false;
            }
            if (previous != null ? !previous.equals(other.previous) : other.previous != null) {
                return false;
            }
            return neighbours != null ? neighbours.equals(other.neighbours) : other.neighbours == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + distance;
            result = 31 * result + (previous != null ? previous.hashCode() : 0);
            result = 31 * result + (neighbours != null ? neighbours.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + distance + ")";
        }
    }

    /**
     * Builds a directed, weighted graph from the given edges.
     *
     * @param edges array of edges defining the graph
     */
    Class2(Edge[] edges) {
        vertices = new HashMap<>(edges.length);

        // Create all vertices referenced by the edges.
        for (Edge edge : edges) {
            vertices.computeIfAbsent(edge.source, Vertex::new);
            vertices.computeIfAbsent(edge.target, Vertex::new);
        }

        // Populate adjacency lists with neighbours and edge weights.
        for (Edge edge : edges) {
            Vertex sourceVertex = vertices.get(edge.source);
            Vertex targetVertex = vertices.get(edge.target);
            sourceVertex.neighbours.put(targetVertex, edge.weight);
        }
    }

    /**
     * Runs Dijkstra's algorithm from the given start vertex.
     *
     * @param startName name of the start vertex
     */
    public void computeShortestPaths(String startName) {
        if (!vertices.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }

        final Vertex source = vertices.get(startName);
        NavigableSet<Vertex> queue = new TreeSet<>();

        // Initialize all vertices: distance 0 for source, infinity for others.
        for (Vertex v : vertices.values()) {
            v.previous = v == source ? source : null;
            v.distance = v == source ? 0 : Integer.MAX_VALUE;
            queue.add(v);
        }

        runDijkstra(queue);
    }

    /**
     * Core Dijkstra loop using a priority queue (TreeSet) ordered by current distance.
     *
     * @param queue priority queue of vertices to process
     */
    private void runDijkstra(final NavigableSet<Vertex> queue) {
        while (!queue.isEmpty()) {
            Vertex u = queue.pollFirst();

            // All remaining vertices are unreachable from the source.
            if (u.distance == Integer.MAX_VALUE) {
                break;
            }

            // Relax edges from u to each of its neighbours.
            for (Map.Entry<Vertex, Integer> entry : u.neighbours.entrySet()) {
                Vertex v = entry.getKey();
                final int alternateDist = u.distance + entry.getValue();

                if (alternateDist < v.distance) {
                    queue.remove(v);
                    v.distance = alternateDist;
                    v.previous = u;
                    queue.add(v);
                }
            }
        }
    }

    /**
     * Prints the shortest path from the start vertex to the given end vertex.
     *
     * @param endName name of the end vertex
     */
    public void printPath(String endName) {
        if (!vertices.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endName);
            return;
        }

        vertices.get(endName).printPath();
        System.out.println();
    }

    /**
     * Prints the shortest paths from the start vertex to all vertices in the graph.
     */
    public void printAllPaths() {
        for (Vertex v : vertices.values()) {
            v.printPath();
            System.out.println();
        }
    }
}