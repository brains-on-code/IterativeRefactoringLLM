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

    private static final String START_VERTEX_NAME = "a";
    private static final String END_VERTEX_NAME = "e";

    public static void main(String[] args) {
        DijkstraGraph graph = new DijkstraGraph(GRAPH_EDGES);
        graph.computeShortestPaths(START_VERTEX_NAME);
        graph.printPath(END_VERTEX_NAME);
    }
}

class DijkstraGraph {

    private final Map<String, Vertex> verticesByName;

    public static class Edge {

        public final String sourceVertexName;
        public final String destinationVertexName;
        public final int weight;

        Edge(String sourceVertexName, String destinationVertexName, int weight) {
            this.sourceVertexName = sourceVertexName;
            this.destinationVertexName = destinationVertexName;
            this.weight = weight;
        }
    }

    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        public int distance = Integer.MAX_VALUE;
        public Vertex previousVertex = null;
        public final Map<Vertex, Integer> neighborWeights = new HashMap<>();

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
        public boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null || getClass() != otherObject.getClass()) {
                return false;
            }
            if (!super.equals(otherObject)) {
                return false;
            }

            Vertex otherVertex = (Vertex) otherObject;

            if (distance != otherVertex.distance) {
                return false;
            }
            if (name != null ? !name.equals(otherVertex.name) : otherVertex.name != null) {
                return false;
            }
            if (previousVertex != null
                    ? !previousVertex.equals(otherVertex.previousVertex)
                    : otherVertex.previousVertex != null) {
                return false;
            }
            return neighborWeights != null
                    ? neighborWeights.equals(otherVertex.neighborWeights)
                    : otherVertex.neighborWeights == null;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + distance;
            result = 31 * result + (previousVertex != null ? previousVertex.hashCode() : 0);
            result = 31 * result + (neighborWeights != null ? neighborWeights.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + distance + ")";
        }
    }

    DijkstraGraph(Edge[] edges) {
        verticesByName = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            if (!verticesByName.containsKey(edge.sourceVertexName)) {
                verticesByName.put(edge.sourceVertexName, new Vertex(edge.sourceVertexName));
            }
            if (!verticesByName.containsKey(edge.destinationVertexName)) {
                verticesByName.put(edge.destinationVertexName, new Vertex(edge.destinationVertexName));
            }
        }

        for (Edge edge : edges) {
            Vertex sourceVertex = verticesByName.get(edge.sourceVertexName);
            Vertex destinationVertex = verticesByName.get(edge.destinationVertexName);
            sourceVertex.neighborWeights.put(destinationVertex, edge.weight);
        }
    }

    public void computeShortestPaths(String startVertexName) {
        if (!verticesByName.containsKey(startVertexName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startVertexName);
            return;
        }
        final Vertex startVertex = verticesByName.get(startVertexName);
        NavigableSet<Vertex> unsettledVertices = new TreeSet<>();

        for (Vertex vertex : verticesByName.values()) {
            vertex.previousVertex = vertex == startVertex ? startVertex : null;
            vertex.distance = vertex == startVertex ? 0 : Integer.MAX_VALUE;
            unsettledVertices.add(vertex);
        }

        computeShortestPaths(unsettledVertices);
    }

    private void computeShortestPaths(final NavigableSet<Vertex> unsettledVertices) {
        while (!unsettledVertices.isEmpty()) {
            Vertex currentVertex = unsettledVertices.pollFirst();
            if (currentVertex.distance == Integer.MAX_VALUE) {
                break;
            }
            for (Map.Entry<Vertex, Integer> neighborEntry :
                    currentVertex.neighborWeights.entrySet()) {
                Vertex neighborVertex = neighborEntry.getKey();
                final int alternateDistance = currentVertex.distance + neighborEntry.getValue();
                if (alternateDistance < neighborVertex.distance) {
                    unsettledVertices.remove(neighborVertex);
                    neighborVertex.distance = alternateDistance;
                    neighborVertex.previousVertex = currentVertex;
                    unsettledVertices.add(neighborVertex);
                }
            }
        }
    }

    public void printPath(String endVertexName) {
        if (!verticesByName.containsKey(endVertexName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endVertexName);
            return;
        }

        verticesByName.get(endVertexName).printPath();
        System.out.println();
    }

    public void printAllPaths() {
        for (Vertex vertex : verticesByName.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}