package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class Graph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    Graph() {
        vertices = new ArrayList<>();
    }

    private class Vertex {

        E value;
        List<Vertex> adjacentVertices;

        Vertex(E value) {
            this.value = value;
            this.adjacentVertices = new ArrayList<>();
        }

        public boolean addEdge(Vertex destination) {
            for (Vertex adjacent : adjacentVertices) {
                if (adjacent.value.compareTo(destination.value) == 0) {
                    return false;
                }
            }
            return adjacentVertices.add(destination);
        }

        public boolean removeEdge(E destinationValue) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).value.compareTo(destinationValue) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean removeEdge(E sourceValue, E destinationValue) {
        Vertex sourceVertex = null;
        for (Vertex vertex : vertices) {
            if (sourceValue.compareTo(vertex.value) == 0) {
                sourceVertex = vertex;
                break;
            }
        }
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeEdge(destinationValue);
    }

    public boolean addEdge(E sourceValue, E destinationValue) {
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (Vertex vertex : vertices) {
            if (sourceValue.compareTo(vertex.value) == 0) {
                sourceVertex = vertex;
            } else if (destinationValue.compareTo(vertex.value) == 0) {
                destinationVertex = vertex;
            }
            if (sourceVertex != null && destinationVertex != null) {
                break;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(sourceValue);
            vertices.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(destinationValue);
            vertices.add(destinationVertex);
        }
        return sourceVertex.addEdge(destinationVertex);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex : vertices) {
            builder.append("Vertex: ");
            builder.append(vertex.value);
            builder.append("\n");
            builder.append("Adjacent vertices: ");
            for (Vertex adjacent : vertex.adjacentVertices) {
                builder.append(adjacent.value);
                builder.append(" ");
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