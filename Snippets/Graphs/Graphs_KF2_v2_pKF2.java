package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple directed graph implementation using an adjacency list.
 *
 * @param <E> the type of data stored in each vertex; must be comparable
 */
class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    AdjacencyListGraph() {
        this.vertices = new ArrayList<>();
    }

    /**
     * Represents a single vertex in the graph.
     */
    private class Vertex {

        private final E data;
        private final List<Vertex> adjacentVertices;

        Vertex(E data) {
            this.data = data;
            this.adjacentVertices = new ArrayList<>();
        }

        /**
         * Adds a directed edge from this vertex to the given vertex.
         * Does nothing if an edge to the same vertex already exists.
         *
         * @param to the vertex to connect to
         * @return {@code true} if the edge was added, {@code false} if it already existed
         */
        boolean addAdjacentVertex(Vertex to) {
            for (Vertex v : adjacentVertices) {
                if (v.data.compareTo(to.data) == 0) {
                    return false;
                }
            }
            return adjacentVertices.add(to);
        }

        /**
         * Removes a directed edge from this vertex to the vertex with the given data.
         *
         * @param to the data of the vertex to disconnect from
         * @return {@code true} if the edge was removed, {@code false} if it did not exist
         */
        boolean removeAdjacentVertex(E to) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).data.compareTo(to) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Finds the vertex that holds the given data.
     *
     * @param data the data to search for
     * @return the vertex containing the data, or {@code null} if not found
     */
    private Vertex findVertex(E data) {
        for (Vertex v : vertices) {
            if (data.compareTo(v.data) == 0) {
                return v;
            }
        }
        return null;
    }

    /**
     * Removes a directed edge from one vertex to another.
     *
     * @param from the data of the source vertex
     * @param to   the data of the destination vertex
     * @return {@code true} if the edge was removed, {@code false} otherwise
     */
    public boolean removeEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeAdjacentVertex(to);
    }

    /**
     * Adds a directed edge from one vertex to another.
     * If either vertex does not exist, it is created.
     *
     * @param from the data of the source vertex
     * @param to   the data of the destination vertex
     * @return {@code true} if the edge was added, {@code false} if it already existed
     */
    public boolean addEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        Vertex toVertex = findVertex(to);

        if (fromVertex == null) {
            fromVertex = new Vertex(from);
            vertices.add(fromVertex);
        }
        if (toVertex == null) {
            toVertex = new Vertex(to);
            vertices.add(toVertex);
        }

        return fromVertex.addAdjacentVertex(toVertex);
    }

    /**
     * Returns a string representation of the graph, listing each vertex
     * and its adjacent vertices.
     *
     * @return a human-readable representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append("Vertex: ").append(v.data).append(System.lineSeparator());
            sb.append("Adjacent vertices: ");
            for (Vertex adjacent : v.adjacentVertices) {
                sb.append(adjacent.data).append(' ');
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

public final class Graphs {

    private Graphs() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
        assert graph.addEdge(1, 2);
        assert graph.addEdge(1, 5);
        assert graph.addEdge(2, 5);
        assert !graph.addEdge(1, 2);
        assert graph.addEdge(2, 3);
        assert graph.addEdge(3, 4);
        assert graph.addEdge(4, 1);
        assert !graph.addEdge(2, 3);
        System.out.println(graph);
    }
}