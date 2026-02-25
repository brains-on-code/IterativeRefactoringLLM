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

        public boolean addNeighbor(Vertex neighborVertex) {
            for (Vertex neighbor : neighbors) {
                if (neighbor.data.compareTo(neighborVertex.data) == 0) {
                    return false;
                }
            }
            return neighbors.add(neighborVertex);
        }

        public boolean removeNeighbor(E neighborData) {
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i).data.compareTo(neighborData) == 0) {
                    neighbors.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean removeEdge(E fromData, E toData) {
        Vertex sourceVertex = null;
        for (Vertex vertex : vertexList) {
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
        Vertex destinationVertex = null;

        for (Vertex vertex : vertexList) {
            if (fromData.compareTo(vertex.data) == 0) {
                sourceVertex = vertex;
            } else if (toData.compareTo(vertex.data) == 0) {
                destinationVertex = vertex;
            }
            if (sourceVertex != null && destinationVertex != null) {
                break;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(fromData);
            vertexList.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(toData);
            vertexList.add(destinationVertex);
        }

        return sourceVertex.addNeighbor(destinationVertex);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex : vertexList) {
            builder.append("Vertex: ")
                   .append(vertex.data)
                   .append("\n")
                   .append("Adjacent vertices: ");
            for (Vertex neighbor : vertex.neighbors) {
                builder.append(neighbor.data).append(" ");
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