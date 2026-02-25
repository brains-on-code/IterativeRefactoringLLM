package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Dijkstra's algorithm is a graph search algorithm that solves the
 * single-source shortest path problem for a graph with nonnegative edge path
 * costs, producing a shortest path tree.
 *
 * <p>
 * NOTE: The inputs to Dijkstra's algorithm are a directed and weighted graph
 * consisting of 2 or more nodes, generally represented by an adjacency matrix
 * or list, and a start node.
 *
 * <p>
 * Original source of code:
 * https://rosettacode.org/wiki/Dijkstra%27s_algorithm#Java
 */
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

    /**
     * Runs Dijkstra's algorithm on the predefined GRAPH.
     */
    public static void main(String[] args) {
        Graph graph = new Graph(GRAPH);
        graph.dijkstra(START);
        graph.printPath(END);
        // graph.printAllPaths();
    }
}

class Graph {

    /** Mapping of vertex names to Vertex objects. */
    private final Map<String, Vertex> vertices;

    /**
     * One edge of the graph (only used by Graph constructor).
     */
    public static class Edge {

        public final String from;
        public final String to;
        public final int distance;

        public Edge(String from, String to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }
    }

    /**
     * One vertex of the graph, complete with mappings to neighbouring vertices.
     */
    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        /** Distance from source; Integer.MAX_VALUE represents infinity. */
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

    /**
     * Builds a graph from a set of edges.
     */
    public Graph(Edge[] edges) {
        this.vertices = new HashMap<>(edges.length);
        initializeVertices(edges);
        initializeNeighbours(edges);
    }

    private void initializeVertices(Edge[] edges) {
        for (Edge edge : edges) {
            vertices.computeIfAbsent(edge.from, Vertex::new);
            vertices.computeIfAbsent(edge.to, Vertex::new);
        }
    }

    private void initializeNeighbours(Edge[] edges) {
        for (Edge edge : edges) {
            Vertex fromVertex = vertices.get(edge.from);
            Vertex toVertex = vertices.get(edge.to);
            fromVertex.neighbours.put(toVertex, edge.distance);
            // For an undirected graph, also add the reverse edge:
            // toVertex.neighbours.put(fromVertex, edge.distance);
        }
    }

    /**
     * Runs Dijkstra's algorithm using a specified source vertex.
     */
    public void dijkstra(String startName) {
        Vertex source = vertices.get(startName);
        if (source == null) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }

        NavigableSet<Vertex> queue = new TreeSet<>();
        initializeForDijkstra(source, queue);
        runDijkstra(queue);
    }

    private void initializeForDijkstra(Vertex source, NavigableSet<Vertex> queue) {
        for (Vertex vertex : vertices.values()) {
            if (vertex.equals(source)) {
                vertex.previous = source;
                vertex.distance = 0;
            } else {
                vertex.previous = null;
                vertex.distance = Integer.MAX_VALUE;
            }
            queue.add(vertex);
        }
    }

    /**
     * Implementation of Dijkstra's algorithm using a binary heap.
     */
    private void runDijkstra(final NavigableSet<Vertex> queue) {
        while (!queue.isEmpty()) {
            Vertex current = queue.pollFirst();
            if (current.distance == Integer.MAX_VALUE) {
                // Remaining vertices are unreachable.
                break;
            }

            updateNeighbourDistances(queue, current);
        }
    }

    private void updateNeighbourDistances(NavigableSet<Vertex> queue, Vertex current) {
        for (Map.Entry<Vertex, Integer> neighbourEntry : current.neighbours.entrySet()) {
            Vertex neighbour = neighbourEntry.getKey();
            int edgeDistance = neighbourEntry.getValue();

            int alternateDistance = current.distance + edgeDistance;
            if (alternateDistance < neighbour.distance) {
                queue.remove(neighbour);
                neighbour.distance = alternateDistance;
                neighbour.previous = current;
                queue.add(neighbour);
            }
        }
    }

    /**
     * Prints a path from the source to the specified vertex.
     */
    public void printPath(String endName) {
        Vertex endVertex = vertices.get(endName);
        if (endVertex == null) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endName);
            return;
        }

        endVertex.printPath();
        System.out.println();
    }

    /**
     * Prints the path from the source to every vertex (output order is not
     * guaranteed).
     */
    public void printAllPaths() {
        for (Vertex vertex : vertices.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}