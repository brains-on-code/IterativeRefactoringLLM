package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class Graph<E extends Comparable<E>> {

    private final List<Vertex> vertexList;

    Graph() {
        this.vertexList = new ArrayList<>();
    }

    private class Vertex {

        E value;
        List<Vertex> adjacentVertices;

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

        public boolean removeNeighbor(E neighborValue) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).value.compareTo(neighborValue) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean removeEdge(E sourceValue, E destinationValue) {
        Vertex sourceVertex = findVertexByValue(sourceValue);
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeNeighbor(destinationValue);
    }

    public boolean addEdge(E sourceValue, E destinationValue) {
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (Vertex currentVertex : vertexList) {
            if (sourceValue.compareTo(currentVertex.value) == 0) {
                sourceVertex = currentVertex;
            } else if (destinationValue.compareTo(currentVertex.value) == 0) {
                destinationVertex = currentVertex;
            }
            if (sourceVertex != null && destinationVertex != null) {
                break;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(sourceValue);
            vertexList.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(destinationValue);
            vertexList.add(destinationVertex);
        }
        return sourceVertex.addNeighbor(destinationVertex);
    }

    private Vertex findVertexByValue(E value) {
        for (Vertex vertex : vertexList) {
            if (value.compareTo(vertex.value) == 0) {
                return vertex;
            }
        }
        return null;
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