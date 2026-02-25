package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

public final class Dijkstra {

    private Dijkstra() {}

    private static final Graph.Edge[] GRAPH = {
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

    private static final String START = "a";
    private static final String END = "e";

    public static void main(String[] args) {
        Graph graph = new Graph(GRAPH);
        graph.dijkstra(START);
        graph.printPath(END);
    }
}

class Graph {

    private final Map<String, Vertex> graph;

    public static class Edge {

        public final String v1;
        public final String v2;
        public final int dist;

        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    public static class Vertex implements Comparable<Vertex> {

        public final String name;
        public int dist = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        public Vertex(String name) {
            this.name = name;
        }

        private void printPath() {
            if (this == this.previous) {
                System.out.printf("%s", this.name);
            } else if (this.previous == null) {
                System.out.printf("%s(unreached)", this.name);
            } else {
                this.previous.printPath();
                System.out.printf(" -> %s(%d)", this.name, this.dist);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            int distCompare = Integer.compare(this.dist, other.dist);
            if (distCompare != 0) {
                return distCompare;
            }
            return this.name.compareTo(other.name);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Vertex)) {
                return false;
            }
            Vertex vertex = (Vertex) object;
            return dist == vertex.dist
                && Objects.equals(name, vertex.name)
                && Objects.equals(previous, vertex.previous)
                && Objects.equals(neighbours, vertex.neighbours);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, dist, previous, neighbours);
        }

        @Override
        public String toString() {
            return "(" + name + ", " + dist + ")";
        }
    }

    public Graph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            graph.computeIfAbsent(edge.v1, Vertex::new);
            graph.computeIfAbsent(edge.v2, Vertex::new);
        }

        for (Edge edge : edges) {
            Vertex from = graph.get(edge.v1);
            Vertex to = graph.get(edge.v2);
            from.neighbours.put(to, edge.dist);
        }
    }

    public void dijkstra(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", startName);
            return;
        }

        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> queue = new TreeSet<>();

        for (Vertex vertex : graph.values()) {
            if (vertex == source) {
                vertex.previous = source;
                vertex.dist = 0;
            } else {
                vertex.previous = null;
                vertex.dist = Integer.MAX_VALUE;
            }
            queue.add(vertex);
        }

        runDijkstra(queue);
    }

    private void runDijkstra(final NavigableSet<Vertex> queue) {
        while (!queue.isEmpty()) {
            Vertex u = queue.pollFirst();
            if (u.dist == Integer.MAX_VALUE) {
                break;
            }

            for (Map.Entry<Vertex, Integer> neighbourEntry : u.neighbours.entrySet()) {
                Vertex v = neighbourEntry.getKey();
                int weight = neighbourEntry.getValue();

                int alternateDist = u.dist + weight;
                if (alternateDist < v.dist) {
                    queue.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    queue.add(v);
                }
            }
        }
    }

    public void printPath(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", endName);
            return;
        }

        graph.get(endName).printPath();
        System.out.println();
    }

    public void printAllPaths() {
        for (Vertex vertex : graph.values()) {
            vertex.printPath();
            System.out.println();
        }
    }
}