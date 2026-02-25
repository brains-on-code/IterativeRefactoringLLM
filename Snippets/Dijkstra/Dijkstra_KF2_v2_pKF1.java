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

    private final Map<String, Vertex> vertexByName;

    public static class Edge {

        public final String sourceName;
        public final String targetName;
        public final int weight;

        Edge(String sourceName, String targetName, int weight) {
            this.sourceName = sourceName;
            this.targetName = targetName;
            this.weight = weight;
        }
    }

    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        public int distanceFromSource = Integer.MAX_VALUE;
        public Vertex previousVertex = null;
        public final Map<Vertex, Integer> adjacentVertices = new HashMap<>();

        Vertex(String name) {
            this.name = name;
        }

        private void printPath() {
            if (this == this.previousVertex) {
                System.out.printf("%s", this.name);
            } else if (this.previousVertex == null) {
                System.out.printf("%s(unreached)", this.name);
            } else {
                this.previousVertex.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.distanceFromSource);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            if (distanceFromSource == other.distanceFromSource) {
                return name.compareTo(other.name);
            }
            return Integer.compare(distanceFromSource, other.distanceFromSource);
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

            if (distanceFromSource != vertex.distanceFromSource) {
                return false;
            }
            if (name != null ? !name.equals(vertex.name) : vertex.name != null) {
                return false;
            }
            if (previousVertex != null
                    ? !previousVertex.equals(vertex.previousVertex)
                    : vertex.previousVertex != null) {
                return false;
            }
            return adjacentVertices != null
                    ? adjacentVertices.equals(vertex.adjacentVertices)
                    : vertex.adjacentVertices == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + distanceFromSource;
            result = 31 * result + (previousVertex != null ? previousVertex.hashCode() : 0);
            result = 31 * result + (adjacentVertices != null ? adjacentVertices.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + distanceFromSource + ")";
        }
    }

    Graph(Edge[] edges) {
        vertexByName = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            if (!vertexByName.containsKey(edge.sourceName)) {
                vertexByName.put(edge.sourceName, new Vertex(edge.sourceName));
            }
            if (!vertexByName.containsKey(edge.targetName)) {
                vertexByName.put(edge.targetName, new Vertex(edge.targetName));
            }
        }

        for (Edge edge : edges) {
            Vertex sourceVertex = vertexByName.get(edge.sourceName);
            Vertex targetVertex = vertexByName.get(edge.targetName);
            sourceVertex.adjacentVertices.put(targetVertex, edge.weight);
        }
    }

    public void dijkstra(String startVertexName) {
        if (!vertexByName.containsKey(startVertexName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startVertexName);
            return;
        }
        final Vertex sourceVertex = vertexByName.get(startVertexName);
        NavigableSet<Vertex> unvisitedVertices = new TreeSet<>();

        for (Vertex vertex : vertexByName.values()) {
            vertex.previousVertex = vertex == sourceVertex ? sourceVertex : null;
            vertex.distanceFromSource = vertex == sourceVertex ? 0 : Integer.MAX_VALUE;
            unvisitedVertices.add(vertex);
        }

        dijkstra(unvisitedVertices);
    }

    private void dijkstra(final NavigableSet<Vertex> unvisitedVertices) {
        while (!unvisitedVertices.isEmpty()) {
            Vertex currentVertex = unvisitedVertices.pollFirst();
            if (currentVertex.distanceFromSource == Integer.MAX_VALUE) {
                break;
            }
            for (Map.Entry<Vertex, Integer> neighborEntry :
                    currentVertex.adjacentVertices.entrySet()) {
                Vertex neighborVertex = neighborEntry.getKey();
                final int alternateDistance =
                        currentVertex.distanceFromSource + neighborEntry.getValue();
                if (alternateDistance < neighborVertex.distanceFromSource) {
                    unvisitedVertices.remove(neighborVertex);
                    neighborVertex.distanceFromSource = alternateDistance;
                    neighborVertex.previousVertex = currentVertex;
                    unvisitedVertices.add(neighborVertex);
                }
            }
        }
    }

    public void printPath(String endVertexName) {
        if (!vertexByName.containsKey(endVertexName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endVertexName);
            return;
        }

        vertexByName.get(endVertexName).printPath();
        System.out.println();
    }

    public void printAllPaths() {
        for (Vertex vertex : vertexByName.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}