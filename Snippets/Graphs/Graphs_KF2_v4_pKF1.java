package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class AdjacencyListGraph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    AdjacencyListGraph() {
        this.vertices = new ArrayList<>();
    }

    private class Vertex {

        private final E data;
        private final List<Vertex> neighbors;

        Vertex(E data) {
            this.data = data;
            this.neighbors = new ArrayList<>();
        }

        public boolean addNeighbor(Vertex neighbor) {
            for (Vertex existingNeighbor : neighbors) {
                if (existingNeighbor.data.compareTo(neighbor.data) == 0) {
                    return false;
                }
            }
            return neighbors.add(neighbor);
        }

        public boolean removeNeighbor(E dataToRemove) {
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i).data.compareTo(dataToRemove) == 0) {
                    neighbors.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean removeEdge(E fromData, E toData) {
        Vertex sourceVertex = null;
        for (Vertex vertex : vertices) {
            if (fromData.compareTo(vertex.data) == 0) {
                sourceVertex = vertex;
                break;
            }
        }
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeNeighbor(toData);
    }

    public boolean addEdge(E fromData, E toData) {
        Vertex sourceVertex = null;
        Vertex targetVertex = null;

        for (Vertex vertex : vertices) {
            if (fromData.compareTo(vertex.data) == 0) {
                sourceVertex = vertex;
            } else if (toData.compareTo(vertex.data) == 0) {
                targetVertex = vertex;
            }
            if (sourceVertex != null && targetVertex != null) {
                break;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(fromData);
            vertices.add(sourceVertex);
        }
        if (targetVertex == null) {
            targetVertex = new Vertex(toData);
            vertices.add(targetVertex);
        }

        return sourceVertex.addNeighbor(targetVertex);
    }

    @Override
    public String toString() {
        StringBuilder graphRepresentation = new StringBuilder();
        for (Vertex vertex : vertices) {
            graphRepresentation
                .append("Vertex: ")
                .append(vertex.data)
                .append("\n")
                .append("Adjacent vertices: ");
            for (Vertex neighbor : vertex.neighbors) {
                graphRepresentation.append(neighbor.data).append(" ");
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