package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class Graph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    Graph() {
        this.vertices = new ArrayList<>();
    }

    private class Vertex {

        private final E value;
        private final List<Vertex> adjacentVertices;

        Vertex(E value) {
            this.value = value;
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
                if (adjacentVertices.get(i).value.compareTo(targetValue) == 0) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex : vertices) {
            builder.append("Vertex: ").append(vertex.value).append("\n");
            builder.append("Adjacent vertices: ");
            for (Vertex adjacent : vertex.adjacentVertices) {
                builder.append(adjacent.value).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}

public final class GraphDemo {
    private GraphDemo() {
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