package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

class Graph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    Graph() {
        this.vertices = new ArrayList<>();
    }

    private class Vertex {

        E data;
        List<Vertex> neighbors;

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

    public boolean removeEdge(E sourceData, E destinationData) {
        Vertex sourceVertex = findVertexByData(sourceData);
        if (sourceVertex == null) {
            return false;
        }
        return sourceVertex.removeNeighbor(destinationData);
    }

    public boolean addEdge(E sourceData, E destinationData) {
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (Vertex vertex : vertices) {
            if (sourceData.compareTo(vertex.data) == 0) {
                sourceVertex = vertex;
            } else if (destinationData.compareTo(vertex.data) == 0) {
                destinationVertex = vertex;
            }
            if (sourceVertex != null && destinationVertex != null) {
                break;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(sourceData);
            vertices.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(destinationData);
            vertices.add(destinationVertex);
        }
        return sourceVertex.addNeighbor(destinationVertex);
    }

    private Vertex findVertexByData(E data) {
        for (Vertex vertex : vertices) {
            if (data.compareTo(vertex.data) == 0) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder graphRepresentation = new StringBuilder();
        for (Vertex vertex : vertices) {
            graphRepresentation.append("Vertex: ")
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