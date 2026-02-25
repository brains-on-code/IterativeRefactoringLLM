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
        private final List<Vertex> adjacentVertices;

        Vertex(E data) {
            this.data = data;
            this.adjacentVertices = new ArrayList<>();
        }

        boolean addAdjacentVertex(Vertex to) {
            for (Vertex v : adjacentVertices) {
                if (v.data.compareTo(to.data) == 0) {
                    return false;
                }
            }
            return adjacentVertices.add(to);
        }

        boolean removeAdjacentVertex(E to) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).data.compareTo(to) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    private Vertex findVertex(E data) {
        for (Vertex v : vertices) {
            if (data.compareTo(v.data) == 0) {
                return v;
            }
        }
        return null;
    }

    public boolean removeEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        if (fromVertex == null) {
            return false;
        }
        return fromVertex.removeAdjacentVertex(to);
    }

    public boolean addEdge(E from, E to) {
        Vertex fromVertex = findVertex(from);
        Vertex toVertex = findVertex(to);

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append("Vertex: ").append(v.data).append("\n");
            sb.append("Adjacent vertices: ");
            for (Vertex adjacent : v.adjacentVertices) {
                sb.append(adjacent.data).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

public final class Graphs {

    private Graphs() {}

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