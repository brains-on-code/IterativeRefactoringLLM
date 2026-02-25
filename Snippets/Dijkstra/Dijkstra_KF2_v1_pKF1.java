package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public final class Dijkstra {
    private Dijkstra() {}

    private static final Graph.Edge[] GRAPH_EDGES = {
        new Graph.Edge("a", "b", 7),
        new Graph.Edge("a", "c", 9),
        new Graph.Edge("a", "f", 14),
        new Graph.Edge("b", "c", 10),
        new Graph.Edge("b", "d", 15),
        new Graph.Edge("c", "d", 11),
        new Graph.Edge("c", "f", 2),
        new Graph.Edge("d", "e", 6),
        new Graph.Edge("e", "f", 9),
    };

    private static final String START_VERTEX_NAME = "a";
    private static final String END_VERTEX_NAME = "e";

    public static void main(String[] args) {
        Graph graph = new Graph(GRAPH_EDGES);
        graph.dijkstra(START_VERTEX_NAME);
        graph.printPath(END_VERTEX_NAME);
    }
}

class Graph {

    private final Map<String, Vertex> vertices;

    public static class Edge {

        public final String sourceName;
        public final String targetName;
        public final int distance;

        Edge(String sourceName, String targetName, int distance) {
            this.sourceName = sourceName;
            this.targetName = targetName;
            this.distance = distance;
        }
    }

    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        public int distance = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbors = new HashMap<>();

        Vertex(String name) {
            this.name = name;
        }

        private void printPath() {
            if (this == this.previous) {
                System.out.printf("%s", this.name);
            } else if (this.previous == null) {
                System.out.printf("%s(unreached)", this.name);
            } else {
                this.previous.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.distance);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            if (distance == other.distance) {
                return name.compareTo(other.name);
            }
            return Integer.compare(distance, other.distance);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            if (!super.equals(object)) {
                return false;
            }

            Vertex vertex = (Vertex) object;

            if (distance != vertex.distance) {
                return false;
            }
            if (name != null ? !name.equals(vertex.name) : vertex.name != null) {
                return false;
            }
            if (previous != null ? !previous.equals(vertex.previous) : vertex.previous != null) {
                return false;
            }
            return neighbors != null ? neighbors.equals(vertex.neighbors) : vertex.neighbors == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + distance;
            result = 31 * result + (previous != null ? previous.hashCode() : 0);
            result = 31 * result + (neighbors != null ? neighbors.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + distance + ")";
        }
    }

    Graph(Edge[] edges) {
        vertices = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            if (!vertices.containsKey(edge.sourceName)) {
                vertices.put(edge.sourceName, new Vertex(edge.sourceName));
            }
            if (!vertices.containsKey(edge.targetName)) {
                vertices.put(edge.targetName, new Vertex(edge.targetName));
            }
        }

        for (Edge edge : edges) {
            vertices.get(edge.sourceName).neighbors.put(vertices.get(edge.targetName), edge.distance);
        }
    }

    public void dijkstra(String startVertexName) {
        if (!vertices.containsKey(startVertexName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startVertexName);
            return;
        }
        final Vertex sourceVertex = vertices.get(startVertexName);
        NavigableSet<Vertex> unvisitedVertices = new TreeSet<>();

        for (Vertex vertex : vertices.values()) {
            vertex.previous = vertex == sourceVertex ? sourceVertex : null;
            vertex.distance = vertex == sourceVertex ? 0 : Integer.MAX_VALUE;
            unvisitedVertices.add(vertex);
        }

        dijkstra(unvisitedVertices);
    }

    private void dijkstra(final NavigableSet<Vertex> unvisitedVertices) {
        while (!unvisitedVertices.isEmpty()) {
            Vertex currentVertex = unvisitedVertices.pollFirst();
            if (currentVertex.distance == Integer.MAX_VALUE) {
                break;
            }
            for (Map.Entry<Vertex, Integer> neighborEntry : currentVertex.neighbors.entrySet()) {
                Vertex neighborVertex = neighborEntry.getKey();
                final int alternateDistance = currentVertex.distance + neighborEntry.getValue();
                if (alternateDistance < neighborVertex.distance) {
                    unvisitedVertices.remove(neighborVertex);
                    neighborVertex.distance = alternateDistance;
                    neighborVertex.previous = currentVertex;
                    unvisitedVertices.add(neighborVertex);
                }
            }
        }
    }

    public void printPath(String endVertexName) {
        if (!vertices.containsKey(endVertexName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endVertexName);
            return;
        }

        vertices.get(endVertexName).printPath();
        System.out.println();
    }

    public void printAllPaths() {
        for (Vertex vertex : vertices.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}