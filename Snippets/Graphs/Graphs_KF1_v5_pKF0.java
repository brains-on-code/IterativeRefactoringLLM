package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Graph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    Graph() {
        this.vertices = new ArrayList<>();
    }

    private class Vertex {

        private final E value;
        private final List<Vertex> adjacentVertices;

        Vertex(E value) {
            this.value = Objects.requireNonNull(value, "Vertex value cannot be null");
            this.adjacentVertices = new ArrayList<>();
        }

        boolean addEdge(Vertex target) {
            if (isAdjacentTo(target.value)) {
                return false;
            }
            return adjacentVertices.add(target);
        }

        boolean removeEdge(E targetValue) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                Vertex adjacent = adjacentVertices.get(i);
                if (adjacent.value.compareTo(targetValue) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }

        private boolean isAdjacentTo(E targetValue) {
            for (Vertex vertex : adjacentVertices) {
                if (vertex.value.compareTo(targetValue) == 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean addEdge(E from, E to) {
        Vertex fromVertex = findOrCreateVertex(from);
        Vertex toVertex = findOrCreateVertex(to);
        return fromVertex.addEdge(toVertex);
    }

    public boolean removeEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeEdge(to);
    }

    private Vertex findVertex(E value) {
        for (Vertex vertex : vertices) {
            if (value.compareTo(vertex.value) == 0) {
                return vertex;
            }
        }
        return null;
    }

    private Vertex findOrCreateVertex(E value) {
        Vertex vertex = findVertex(value);
        if (vertex == null) {
            vertex = new Vertex(value);
            vertices.add(vertex);
        }
        return vertex;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        for (Vertex vertex : vertices) {
            builder.append("Vertex: ")
                   .append(vertex.value)
                   .append(lineSeparator)
                   .append("Adjacent vertices: ");

            for (Vertex adjacent : vertex.adjacentVertices) {
                builder.append(adjacent.value).append(" ");
            }

            builder.append(lineSeparator);
        }

        return builder.toString();
    }
}

public final class GraphDemo {

    private GraphDemo() {
        // Utility class
    }

    public static void main(String[] args) {
        Graph<Integer> graph = new Graph<>();
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