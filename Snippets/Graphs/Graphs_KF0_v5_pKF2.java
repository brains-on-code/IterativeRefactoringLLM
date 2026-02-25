package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple directed graph implementation backed by an adjacency list.
 *
 * @param <E> the type of data stored in each vertex
 */
class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertices = new ArrayList<>();

    /**
     * Represents a single vertex in the graph along with its outgoing edges.
     */
    private class Vertex {

        private final E data;
        private final List<Vertex> adjacentVertices = new ArrayList<>();

        Vertex(E data) {
            this.data = data;
        }

        /**
         * Adds a directed edge from this vertex to the specified vertex if it does not already exist.
         *
         * @param to the vertex to connect to
         * @return {@code true} if the edge was added, {@code false} if it already existed
         */
        boolean addAdjacentVertex(Vertex to) {
            for (Vertex adjacent : adjacentVertices) {
                if (adjacent.data.compareTo(to.data) == 0) {
                    return false;
                }
            }
            return adjacentVertices.add(to);
        }

        /**
         * Removes a directed edge from this vertex to the vertex with the given data.
         *
         * <p>Removal is done by index instead of {@link List#remove(Object)} to avoid
         * relying on {@code equals} for {@link Vertex}.</p>
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
     * Adds a directed edge between two vertices, creating the vertices if necessary.
     *
     * @param from the data of the source vertex
     * @param to   the data of the destination vertex
     * @return {@code true} if the edge was added,
     *         {@code false} if the edge already existed
     */
    public boolean addEdge(E from, E to) {
        Vertex fromVertex = findOrCreateVertex(from);
        Vertex toVertex = findOrCreateVertex(to);
        return fromVertex.addAdjacentVertex(toVertex);
    }

    /**
     * Removes a directed edge from one vertex to another.
     *
     * @param from the data of the source vertex
     * @param to   the data of the destination vertex
     * @return {@code true} if the edge existed and was removed,
     *         {@code false} if the edge or source vertex did not exist
     */
    public boolean removeEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeAdjacentVertex(to);
    }

    /**
     * Returns a textual representation of the graph, listing each vertex and its adjacencies.
     *
     * @return a string describing this graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : vertices) {
            sb.append("Vertex: ").append(vertex.data).append(System.lineSeparator());
            sb.append("Adjacent vertices: ");
            for (Vertex adjacent : vertex.adjacentVertices) {
                sb.append(adjacent.data).append(' ');
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Finds a vertex by its data.
     *
     * @param data the data to search for
     * @return the vertex with the given data, or {@code null} if not found
     */
    private Vertex findVertex(E data) {
        for (Vertex vertex : vertices) {
            if (data.compareTo(vertex.data) == 0) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * Finds a vertex by its data, creating and adding it to the graph if it does not exist.
     *
     * @param data the data to search for or create
     * @return the existing or newly created vertex
     */
    private Vertex findOrCreateVertex(E data) {
        Vertex vertex = findVertex(data);
        if (vertex == null) {
            vertex = new Vertex(data);
            vertices.add(vertex);
        }
        return vertex;
    }
}

/**
 * Demonstrates usage of {@link AdjacencyListGraph}.
 */
public final class Graphs {

    private Graphs() {
        // Prevent instantiation of utility class.
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