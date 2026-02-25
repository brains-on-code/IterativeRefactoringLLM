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
                    return false; // the edge already exists
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

    /**
     * Removes an edge from the graph between two specified vertices.
     *
     * @param from the value of the vertex the edge is from
     * @param to   the value of the vertex the edge is going to
     * @return false if the edge doesn't exist, true if the edge exists and is removed
     */
    public boolean removeEdge(E from, E to) {
        Vertex fromVertex = null;
        for (Vertex vertex : vertices) {
            if (from.compareTo(vertex.value) == 0) {
                fromVertex = vertex;
                break;
            }
        }
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeAdjacentVertex(to);
    }

    /**
     * Adds an edge to the graph between two specified vertices.
     *
     * @param from the value of the vertex the edge is from
     * @param to   the value of the vertex the edge is going to
     * @return true if the edge did not exist, false if it already did
     */
    public boolean addEdge(E from, E to) {
        Vertex fromVertex = null;
        Vertex toVertex = null;

        for (Vertex vertex : vertices) {
            if (from.compareTo(vertex.value) == 0) {
                fromVertex = vertex;
            } else if (to.compareTo(vertex.value) == 0) {
                toVertex = vertex;
            }
            if (fromVertex != null && toVertex != null) {
                break;
            }
        }

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
     * Provides a list of vertices in the graph and their adjacencies.
     *
     * @return a string describing this graph
     */
    @Override
    public String toString() {
        StringBuilder descriptionBuilder = new StringBuilder();
        for (Vertex vertex : vertices) {
            descriptionBuilder.append("Vertex: ")
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