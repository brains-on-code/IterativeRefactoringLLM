package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertices = new ArrayList<>();

    private class Vertex {

        private final E data;
        private final List<Vertex> adjacentVertices = new ArrayList<>();

        Vertex(E data) {
            this.data = Objects.requireNonNull(data, "Vertex data cannot be null");
        }

        boolean addAdjacentVertex(Vertex to) {
            if (isAdjacent(to.data)) {
                return false;
            }
            return adjacentVertices.add(to);
        }

        boolean removeAdjacentVertex(E to) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).data.compareTo(to) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }

        boolean isAdjacent(E data) {
            for (Vertex vertex : adjacentVertices) {
                if (vertex.data.compareTo(data) == 0) {
                    return true;
                }
            }
            return false;
        }
    }

    private Vertex findVertex(E data) {
        for (Vertex vertex : vertices) {
            if (data.compareTo(vertex.data) == 0) {
                return vertex;
            }
        }
        return null;
    }

    private Vertex getOrCreateVertex(E data) {
        Vertex vertex = findVertex(data);
        if (vertex == null) {
            vertex = new Vertex(data);
            vertices.add(vertex);
        }
        return vertex;
    }

    /**
     * Removes an edge from the graph between two specified vertices.
     *
     * @param from the data of the vertex the edge is from
     * @param to   the data of the vertex the edge is going to
     * @return false if the edge doesn't exist, true if the edge exists and is removed
     */
    public boolean removeEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeAdjacentVertex(to);
    }

    /**
     * Adds an edge to the graph between two specified vertices.
     *
     * @param from the data of the vertex the edge is from
     * @param to   the data of the vertex the edge is going to
     * @return true if the edge did not exist, false if it already did
     */
    public boolean addEdge(E from, E to) {
        Vertex fromVertex = getOrCreateVertex(from);
        Vertex toVertex = getOrCreateVertex(to);
        return fromVertex.addAdjacentVertex(toVertex);
    }

    /**
     * Returns a list of vertices in the graph and their adjacencies.
     *
     * @return a string describing this graph
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex : vertices) {
            builder.append("Vertex: ")
                   .append(vertex.data)
                   .append(System.lineSeparator())
                   .append("Adjacent vertices: ");
            for (Vertex adjacent : vertex.adjacentVertices) {
                builder.append(adjacent.data).append(' ');
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
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