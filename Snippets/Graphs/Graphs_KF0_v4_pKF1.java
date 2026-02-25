package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertexList;

    AdjacencyListGraph() {
        this.vertexList = new ArrayList<>();
    }

    private class Vertex {

        private final E data;
        private final List<Vertex> neighbors;

        Vertex(E data) {
            this.data = data;
            this.neighbors = new ArrayList<>();
        }

        public boolean addNeighbor(Vertex neighborToAdd) {
            for (Vertex existingNeighbor : neighbors) {
                if (existingNeighbor.data.compareTo(neighborToAdd.data) == 0) {
                    return false; // the edge already exists
                }
            }
            return neighbors.add(neighborToAdd);
        }

        public boolean removeNeighbor(E neighborDataToRemove) {
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i).data.compareTo(neighborDataToRemove) == 0) {
                    neighbors.remove(i);
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
        for (Vertex vertex : vertexList) {
            if (fromValue.compareTo(vertex.data) == 0) {
                sourceVertex = vertex;
                break;
            }
        }
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeNeighbor(toValue);
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

        for (Vertex vertex : vertexList) {
            if (fromValue.compareTo(vertex.data) == 0) {
                sourceVertex = vertex;
            } else if (toValue.compareTo(vertex.data) == 0) {
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

    /**
     * Provides a list of vertices in the graph and their adjacencies.
     *
     * @return a string describing this graph
     */
    @Override
    public String toString() {
        StringBuilder descriptionBuilder = new StringBuilder();
        for (Vertex vertex : vertexList) {
            descriptionBuilder
                    .append("Vertex: ")
                    .append(vertex.data)
                    .append("\n")
                    .append("Adjacent vertices: ");
            for (Vertex neighbor : vertex.neighbors) {
                descriptionBuilder.append(neighbor.data).append(" ");
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