package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Dijkstra's algorithm implementation for single-source shortest paths
 * on a directed, weighted graph with nonnegative edge weights.
 *
 * <p>Example graph and usage are provided in {@link #main(String[])}.</p>
 */
public final class Dijkstra {

    private Dijkstra() {}

    /**
     * Example graph edges.
     *
     * <p>Each edge is directed: from {@code v1} to {@code v2} with weight {@code dist}.</p>
     */
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

    /**
     * Runs Dijkstra's algorithm on the example graph and prints the path
     * from {@code START} to {@code END}.
     */
    public static void main(String[] args) {
        Graph g = new Graph(GRAPH);
        g.dijkstra(START);
        g.printPath(END);
        // g.printAllPaths();
    }
}

class Graph {

    /** Mapping of vertex names to {@link Vertex} instances. */
    private final Map<String, Vertex> graph;

    /**
     * One directed, weighted edge of the graph.
     */
    public static class Edge {

        public final String v1;
        public final String v2;
        public final int dist;

        Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    /**
     * One vertex of the graph, with distances and neighbours.
     */
    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        /** Current shortest distance from the source; {@link Integer#MAX_VALUE} means "infinity". */
        public int dist = Integer.MAX_VALUE;
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
                System.out.printf(" -> %s(%d)", this.name, this.dist);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            if (dist == other.dist) {
                return name.compareTo(other.name);
            }
            return Integer.compare(dist, other.dist);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            if (!super.equals(object)) {
                return false;
            }

            Vertex vertex = (Vertex) object;

            if (dist != vertex.dist) {
                return false;
            }
            if (name != null ? !name.equals(vertex.name) : vertex.name != null) {
                return false;
            }
            if (previous != null ? !previous.equals(vertex.previous) : vertex.previous != null) {
                return false;
            }
            return neighbours != null ? neighbours.equals(vertex.neighbours) : vertex.neighbours == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + dist;
            result = 31 * result + (previous != null ? previous.hashCode() : 0);
            result = 31 * result + (neighbours != null ? neighbours.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + dist + ")";
        }
    }

    /**
     * Builds a directed graph from an array of edges.
     *
     * <p>For an undirected graph, add the reverse edge as well
     * (see commented line in the second loop).</p>
     */
    Graph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        // First pass: create all vertices.
        for (Edge e : edges) {
            graph.computeIfAbsent(e.v1, Vertex::new);
            graph.computeIfAbsent(e.v2, Vertex::new);
        }

        // Second pass: connect vertices with edges.
        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            // For an undirected graph, also add the reverse edge:
            // graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist);
        }
    }

    /**
     * Runs Dijkstra's algorithm from the given start vertex name.
     */
    public void dijkstra(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // Initialize vertices.
        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }

    /**
     * Core Dijkstra's algorithm using a {@link TreeSet} as a priority queue.
     */
    private void dijkstra(final NavigableSet<Vertex> q) {
        while (!q.isEmpty()) {
            // Vertex with the smallest distance (source on first iteration).
            Vertex u = q.pollFirst();
            if (u.dist == Integer.MAX_VALUE) {
                // Remaining vertices are unreachable from the source.
                break;
            }

            // Relax edges from u to each neighbour.
            for (Map.Entry<Vertex, Integer> entry : u.neighbours.entrySet()) {
                Vertex v = entry.getKey();
                int alternateDist = u.dist + entry.getValue();

                if (alternateDist < v.dist) {
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    /**
     * Prints the shortest path from the source to the specified end vertex.
     */
    public void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endName);
            return;
        }

        graph.get(endName).printPath();
        System.out.println();
    }

    /**
     * Prints the shortest path from the source to every vertex.
     * Output order is not guaranteed.
     */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}