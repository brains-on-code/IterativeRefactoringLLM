package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Dijkstra's algorithm,is a graph search algorithm that solves the
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
 * https://rosettacode.org/wiki/Dijkstra%27s_algorithm#Java Also most of the
 * comments are from RosettaCode.
 */
public final class Dijkstra {
    private Dijkstra() {}

    private static final Graph.Edge[] GRAPH_EDGES = {
        // Distance from node "a" to node "b" is 7.
        // In the current Graph there is no way to move the other way (e,g, from "b" to "a"),
        // a new edge would be needed for that
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

    private static final String START_VERTEX_NAME = "a";
    private static final String END_VERTEX_NAME = "e";

    /**
     * main function Will run the code with "GRAPH_EDGES" that was defined above.
     */
    public static void main(String[] args) {
        Graph graph = new Graph(GRAPH_EDGES);
        graph.dijkstra(START_VERTEX_NAME);
        graph.printPath(END_VERTEX_NAME);
        // graph.printAllPaths();
    }
}

class Graph {

    // mapping of vertex names to Vertex objects, built from a set of Edges
    private final Map<String, Vertex> verticesByName;

    /**
     * One edge of the graph (only used by Graph constructor)
     */
    public static class Edge {

        public final String sourceName;
        public final String targetName;
        public final int weight;

        Edge(String sourceName, String targetName, int weight) {
            this.sourceName = sourceName;
            this.targetName = targetName;
            this.weight = weight;
        }
    }

    /**
     * One vertex of the graph, complete with mappings to neighbouring vertices
     */
    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        // MAX_VALUE assumed to be infinity
        public int distanceFromSource = Integer.MAX_VALUE;
        public Vertex previousVertex = null;
        public final Map<Vertex, Integer> neighborToWeight = new HashMap<>();

        Vertex(String name) {
            this.name = name;
        }

        private void printPath() {
            if (this == this.previousVertex) {
                System.out.printf("%s", this.name);
            } else if (this.previousVertex == null) {
                System.out.printf("%s(unreached)", this.name);
            } else {
                this.previousVertex.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.distanceFromSource);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            if (distanceFromSource == other.distanceFromSource) {
                return name.compareTo(other.name);
            }

            return Integer.compare(distanceFromSource, other.distanceFromSource);
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

            if (distanceFromSource != vertex.distanceFromSource) {
                return false;
            }
            if (name != null ? !name.equals(vertex.name) : vertex.name != null) {
                return false;
            }
            if (previousVertex != null
                    ? !previousVertex.equals(vertex.previousVertex)
                    : vertex.previousVertex != null) {
                return false;
            }
            return neighborToWeight != null
                    ? neighborToWeight.equals(vertex.neighborToWeight)
                    : vertex.neighborToWeight == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + distanceFromSource;
            result = 31 * result + (previousVertex != null ? previousVertex.hashCode() : 0);
            result = 31 * result + (neighborToWeight != null ? neighborToWeight.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + distanceFromSource + ")";
        }
    }

    /**
     * Builds a graph from a set of edges
     */
    Graph(Edge[] edges) {
        verticesByName = new HashMap<>(edges.length);

        // one pass to find all vertices
        for (Edge edge : edges) {
            if (!verticesByName.containsKey(edge.sourceName)) {
                verticesByName.put(edge.sourceName, new Vertex(edge.sourceName));
            }
            if (!verticesByName.containsKey(edge.targetName)) {
                verticesByName.put(edge.targetName, new Vertex(edge.targetName));
            }
        }

        // another pass to set neighbouring vertices
        for (Edge edge : edges) {
            verticesByName
                .get(edge.sourceName)
                .neighborToWeight
                .put(verticesByName.get(edge.targetName), edge.weight);
            // verticesByName.get(edge.targetName).neighborToWeight.put(verticesByName.get(edge.sourceName), edge.weight); // also do this for an
            // undirected graph
        }
    }

    /**
     * Runs dijkstra using a specified source vertex
     */
    public void dijkstra(String startVertexName) {
        if (!verticesByName.containsKey(startVertexName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startVertexName);
            return;
        }
        final Vertex sourceVertex = verticesByName.get(startVertexName);
        NavigableSet<Vertex> unvisitedVertices = new TreeSet<>();

        // set-up vertices
        for (Vertex vertex : verticesByName.values()) {
            vertex.previousVertex = vertex == sourceVertex ? sourceVertex : null;
            vertex.distanceFromSource = vertex == sourceVertex ? 0 : Integer.MAX_VALUE;
            unvisitedVertices.add(vertex);
        }

        dijkstra(unvisitedVertices);
    }

    /**
     * Implementation of dijkstra's algorithm using a binary heap.
     */
    private void dijkstra(final NavigableSet<Vertex> unvisitedVertices) {
        while (!unvisitedVertices.isEmpty()) {
            // vertex with shortest distance (first iteration will return source)
            Vertex currentVertex = unvisitedVertices.pollFirst();
            if (currentVertex.distanceFromSource == Integer.MAX_VALUE) {
                break; // we can ignore currentVertex (and any other remaining vertices) since they are
                       // unreachable
            }
            // look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> neighborEntry : currentVertex.neighborToWeight.entrySet()) {
                Vertex neighborVertex = neighborEntry.getKey(); // the neighbour in this iteration

                final int alternateDistance =
                        currentVertex.distanceFromSource + neighborEntry.getValue();
                if (alternateDistance < neighborVertex.distanceFromSource) { // shorter path to neighbour found
                    unvisitedVertices.remove(neighborVertex);
                    neighborVertex.distanceFromSource = alternateDistance;
                    neighborVertex.previousVertex = currentVertex;
                    unvisitedVertices.add(neighborVertex);
                }
            }
        }
    }

    /**
     * Prints a path from the source to the specified vertex
     */
    public void printPath(String endVertexName) {
        if (!verticesByName.containsKey(endVertexName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endVertexName);
            return;
        }

        verticesByName.get(endVertexName).printPath();
        System.out.println();
    }

    /**
     * Prints the path from the source to every vertex (output order is not
     * guaranteed)
     */
    public void printAllPaths() {
        for (Vertex vertex : verticesByName.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}