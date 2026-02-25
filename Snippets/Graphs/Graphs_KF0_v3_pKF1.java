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

        public boolean addAdjacentVertex(Vertex vertexToAdd) {
            for (Vertex existingAdjacentVertex : adjacentVertices) {
                if (existingAdjacentVertex.value.compareTo(vertexToAdd.value) == 0) {
                    return false; // the edge already exists
                }
            }
            return adjacentVertices.add(vertexToAdd);
        }

        public boolean removeAdjacentVertex(E valueToRemove) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).value.compareTo(valueToRemove) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Removes an edge from the graph between two specified vertices.
     *
     * @param fromValue the value of the vertex the edge is from
     * @param toValue   the value of the vertex the edge is going to
     * @return false if the edge doesn't exist, true if the edge exists and is removed
     */
    public boolean removeEdge(E fromValue, E toValue) {
        Vertex sourceVertex = null;
        for (Vertex vertex : vertices) {
            if (fromValue.compareTo(vertex.value) == 0) {
                sourceVertex = vertex;
                break;
            }
        }
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeAdjacentVertex(toValue);
    }

    /**
     * Adds an edge to the graph between two specified vertices.
     *
     * @param fromValue the value of the vertex the edge is from
     * @param toValue   the value of the vertex the edge is going to
     * @return true if the edge did not exist, false if it already did
     */
    public boolean addEdge(E fromValue, E toValue) {
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (Vertex vertex : vertices) {
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
            vertices.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(toValue);
            vertices.add(destinationVertex);
        }

        return sourceVertex.addAdjacentVertex(destinationVertex);
    }

    /**
     * Provides a list of vertices in the graph and their adjacencies.
     *
     * @return a string describing this graph
     */
    @Override
    public String toString() {
        StringBuilder descriptionBuilder = new StringBuilder();
        for (Vertex vertex : vertices) {
            descriptionBuilder
                    .append("Vertex: ")
                    .append(vertex.value)
                    .append("\n")
                    .append("Adjacent vertices: ");
            for (Vertex adjacentVertex : vertex.adjacentVertices) {
                descriptionBuilder.append(adjacentVertex.value).append(" ");
            }
            descriptionBuilder.append("\n");
        }
        return descriptionBuilder.toString();
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