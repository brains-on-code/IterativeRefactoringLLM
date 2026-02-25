package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public final class DijkstraExample {
    private DijkstraExample() {}

    private static final DijkstraGraph.Edge[] GRAPH_EDGES = {
        new DijkstraGraph.Edge("a", "b", 7),
        new DijkstraGraph.Edge("a", "c", 9),
        new DijkstraGraph.Edge("a", "f", 14),
        new DijkstraGraph.Edge("b", "c", 10),
        new DijkstraGraph.Edge("b", "d", 15),
        new DijkstraGraph.Edge("c", "d", 11),
        new DijkstraGraph.Edge("c", "f", 2),
        new DijkstraGraph.Edge("d", "e", 6),
        new DijkstraGraph.Edge("e", "f", 9),
    };

    private static final String START_VERTEX = "a";
    private static final String END_VERTEX = "e";

    public static void main(String[] args) {
        DijkstraGraph graph = new DijkstraGraph(GRAPH_EDGES);
        graph.computeShortestPaths(START_VERTEX);
        graph.printPath(END_VERTEX);
    }
}

class DijkstraGraph {

    private final Map<String, Vertex> vertices;

    public static class Edge {

        public final String source;
        public final String destination;
        public final int weight;

        Edge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
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
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            if (!super.equals(obj)) {
                return false;
            }

            Vertex other = (Vertex) obj;

            if (distance != other.distance) {
                return false;
            }
            if (name != null ? !name.equals(other.name) : other.name != null) {
                return false;
            }
            if (previous != null ? !previous.equals(other.previous) : other.previous != null) {
                return false;
            }
            return neighbors != null ? neighbors.equals(other.neighbors) : other.neighbors == null;
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

    DijkstraGraph(Edge[] edges) {
        vertices = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            if (!vertices.containsKey(edge.source)) {
                vertices.put(edge.source, new Vertex(edge.source));
            }
            if (!vertices.containsKey(edge.destination)) {
                vertices.put(edge.destination, new Vertex(edge.destination));
            }
        }

        for (Edge edge : edges) {
            vertices.get(edge.source).neighbors.put(vertices.get(edge.destination), edge.weight);
        }
    }

    public void computeShortestPaths(String startVertexName) {
        if (!vertices.containsKey(startVertexName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startVertexName);
            return;
        }
        final Vertex sourceVertex = vertices.get(startVertexName);
        NavigableSet<Vertex> priorityQueue = new TreeSet<>();

        for (Vertex vertex : vertices.values()) {
            vertex.previous = vertex == sourceVertex ? sourceVertex : null;
            vertex.distance = vertex == sourceVertex ? 0 : Integer.MAX_VALUE;
            priorityQueue.add(vertex);
        }

        computeShortestPaths(priorityQueue);
    }

    private void computeShortestPaths(final NavigableSet<Vertex> priorityQueue) {
        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.pollFirst();
            if (currentVertex.distance == Integer.MAX_VALUE) {
                break;
            }
            for (Map.Entry<Vertex, Integer> neighborEntry : currentVertex.neighbors.entrySet()) {
                Vertex neighborVertex = neighborEntry.getKey();
                final int alternateDistance = currentVertex.distance + neighborEntry.getValue();
                if (alternateDistance < neighborVertex.distance) {
                    priorityQueue.remove(neighborVertex);
                    neighborVertex.distance = alternateDistance;
                    neighborVertex.previous = currentVertex;
                    priorityQueue.add(neighborVertex);
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