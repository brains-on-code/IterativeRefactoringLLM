package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertexList;

    AdjacencyListGraph() {
        this.vertexList = new ArrayList<>();
    }

    private class Vertex {

        private final E value;
        private final List<Vertex> adjacentVertices;

        Vertex(E value) {
            this.value = value;
            this.adjacentVertices = new ArrayList<>();
        }

        public boolean addNeighbor(Vertex neighborVertex) {
            for (Vertex existingNeighbor : adjacentVertices) {
                if (existingNeighbor.value.compareTo(neighborVertex.value) == 0) {
                    return false;
                }
            }
            return adjacentVertices.add(neighborVertex);
        }

        public boolean removeNeighbor(E valueToRemove) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).value.compareTo(valueToRemove) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean removeEdge(E fromValue, E toValue) {
        Vertex sourceVertex = null;
        for (Vertex vertex : vertexList) {
            if (fromValue.compareTo(vertex.value) == 0) {
                sourceVertex = vertex;
                break;
            }
        }
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeNeighbor(toValue);
    }

    public boolean addEdge(E fromValue, E toValue) {
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (Vertex vertex : vertexList) {
            if (fromValue.compareTo(vertex.value) == 0) {
                sourceVertex = vertex;
            } else if (toValue.compareTo(vertex.value) == 0) {
                destinationVertex = vertex;
            }
            if (sourceVertex != null && destinationVertex != null) {
                break;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(fromValue);
            vertexList.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(toValue);
            vertexList.add(destinationVertex);
        }

        return sourceVertex.addNeighbor(destinationVertex);
    }

    @Override
    public String toString() {
        StringBuilder graphRepresentation = new StringBuilder();
        for (Vertex vertex : vertexList) {
            graphRepresentation
                .append("Vertex: ")
                .append(vertex.value)
                .append("\n")
                .append("Adjacent vertices: ");
            for (Vertex neighbor : vertex.adjacentVertices) {
                graphRepresentation.append(neighbor.value).append(" ");
            }
            graphRepresentation.append("\n");
        }
        return graphRepresentation.toString();
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