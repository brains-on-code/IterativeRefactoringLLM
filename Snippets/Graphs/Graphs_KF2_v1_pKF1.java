package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    AdjacencyListGraph() {
        this.vertices = new ArrayList<>();
    }

    private class Vertex {

        private final E value;
        private final List<Vertex> adjacentVertices;

        Vertex(E value) {
            this.value = value;
            this.adjacentVertices = new ArrayList<>();
        }

        public boolean addAdjacentVertex(Vertex targetVertex) {
            for (Vertex adjacentVertex : adjacentVertices) {
                if (adjacentVertex.value.compareTo(targetVertex.value) == 0) {
                    return false;
                }
            }
            return adjacentVertices.add(targetVertex);
        }

        public boolean removeAdjacentVertex(E targetValue) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).value.compareTo(targetValue) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean removeEdge(E fromValue, E toValue) {
        Vertex fromVertex = null;
        for (Vertex vertex : vertices) {
            if (fromValue.compareTo(vertex.value) == 0) {
                fromVertex = vertex;
                break;
            }
        }
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeAdjacentVertex(toValue);
    }

    public boolean addEdge(E fromValue, E toValue) {
        Vertex fromVertex = null;
        Vertex toVertex = null;

        for (Vertex vertex : vertices) {
            if (fromValue.compareTo(vertex.value) == 0) {
                fromVertex = vertex;
            } else if (toValue.compareTo(vertex.value) == 0) {
                toVertex = vertex;
            }
            if (fromVertex != null && toVertex != null) {
                break;
            }
        }

        if (fromVertex == null) {
            fromVertex = new Vertex(fromValue);
            vertices.add(fromVertex);
        }
        if (toVertex == null) {
            toVertex = new Vertex(toValue);
            vertices.add(toVertex);
        }

        return fromVertex.addAdjacentVertex(toVertex);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex : vertices) {
            builder.append("Vertex: ")
                   .append(vertex.value)
                   .append("\n")
                   .append("Adjacent vertices: ");
            for (Vertex adjacentVertex : vertex.adjacentVertices) {
                builder.append(adjacentVertex.value).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
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