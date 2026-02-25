package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertexList;

    AdjacencyListGraph() {
        this.vertexList = new ArrayList<>();
    }

    private class Vertex {

        private final E element;
        private final List<Vertex> neighbors;

        Vertex(E element) {
            this.element = element;
            this.neighbors = new ArrayList<>();
        }

        public boolean addNeighbor(Vertex neighborVertex) {
            for (Vertex existingNeighbor : neighbors) {
                if (existingNeighbor.element.compareTo(neighborVertex.element) == 0) {
                    return false; // the edge already exists
                }
            }
            return neighbors.add(neighborVertex);
        }

        public boolean removeNeighbor(E neighborElement) {
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i).element.compareTo(neighborElement) == 0) {
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
     * @param fromElement the value of the vertex the edge is from
     * @param toElement   the value of the vertex the edge is going to
     * @return false if the edge doesn't exist, true if the edge exists and is removed
     */
    public boolean removeEdge(E fromElement, E toElement) {
        Vertex sourceVertex = null;
        for (Vertex vertex : vertexList) {
            if (fromElement.compareTo(vertex.element) == 0) {
                sourceVertex = vertex;
                break;
            }
        }
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeNeighbor(toElement);
    }

    /**
     * Adds an edge to the graph between two specified vertices.
     *
     * @param fromElement the value of the vertex the edge is from
     * @param toElement   the value of the vertex the edge is going to
     * @return true if the edge did not exist, false if it already did
     */
    public boolean addEdge(E fromElement, E toElement) {
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (Vertex vertex : vertexList) {
            if (fromElement.compareTo(vertex.element) == 0) {
                sourceVertex = vertex;
            } else if (toElement.compareTo(vertex.element) == 0) {
                destinationVertex = vertex;
            }
            if (sourceVertex != null && destinationVertex != null) {
                break;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(fromElement);
            vertexList.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(toElement);
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
                    .append(vertex.element)
                    .append("\n")
                    .append("Adjacent vertices: ");
            for (Vertex neighbor : vertex.neighbors) {
                descriptionBuilder.append(neighbor.element).append(" ");
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