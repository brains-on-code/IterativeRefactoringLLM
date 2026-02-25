package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    AdjacencyListGraph() {
        this.vertices = new ArrayList<>();
    }

    private class Vertex {

        private final E data;
        private final List<Vertex> adjacentVertices;

        Vertex(E data) {
            this.data = data;
            this.adjacentVertices = new ArrayList<>();
        }

        /**
         * Adds an edge from this vertex to the given vertex if it does not already exist.
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
         * Removes an edge from this vertex to the vertex with the given data.
         *
         * <p>Indexes are used instead of {@link List#remove(Object)} to avoid
         * requiring an {@code equals} implementation on {@link Vertex}.</p>
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
     * Adds a directed edge between two vertices, creating the vertices if necessary.
     *
     * @param from the data of the source vertex
     * @param to   the data of the destination vertex
     * @return {@code true} if the edge was added,
     *         {@code false} if the edge already existed
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
     * Returns a textual representation of the graph, listing each vertex and its adjacencies.
     *
     * @return a string describing this graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append("Vertex: ").append(v.data).append("\n");
            sb.append("Adjacent vertices: ");
            for (Vertex adjacent : v.adjacentVertices) {
                sb.append(adjacent.data).append(" ");
            }
            sb.append("\n");
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
        for (Vertex v : vertices) {
            if (data.compareTo(v.data) == 0) {
                return v;
            }
        }
        return null;
    }
}

public final class Graphs {
    private Graphs() {
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