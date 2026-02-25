package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    AdjacencyListGraph() {
        this.vertices = new ArrayList<>();
    }

    private class Vertex {

        private final E data;
        private final List<Vertex> adjacentVertices;

        Vertex(E data) {
            this.data = Objects.requireNonNull(data, "Vertex data cannot be null");
            this.adjacentVertices = new ArrayList<>();
        }

        boolean addAdjacentVertex(Vertex to) {
            if (isAdjacent(to.data)) {
                return false;
            }
            return adjacentVertices.add(to);
        }

        boolean removeAdjacentVertex(E to) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (areEqual(adjacentVertices.get(i).data, to)) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }

        private boolean isAdjacent(E data) {
            for (Vertex vertex : adjacentVertices) {
                if (areEqual(vertex.data, data)) {
                    return true;
                }
            }
            return false;
        }
    }

    private Vertex findVertex(E data) {
        for (Vertex vertex : vertices) {
            if (areEqual(data, vertex.data)) {
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

    public boolean addEdge(E from, E to) {
        Vertex fromVertex = getOrCreateVertex(from);
        Vertex toVertex = getOrCreateVertex(to);
        return fromVertex.addAdjacentVertex(toVertex);
    }

    public boolean removeEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeAdjacentVertex(to);
    }

    private boolean areEqual(E first, E second) {
        return first.compareTo(second) == 0;
    }

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