package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
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
        // To print all shortest paths from START to every vertex, uncomment:
        // graph.printAllPaths();
    }
}

class Graph {

    private final Map<String, Vertex> graph;

    public static class Edge {

        public final String v1;
        public final String v2;
        public final int dist;

        Edge(String v1, String v2, int dist) {
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
                System.out.printf(" -> %s(%d)", this.name, this.dist);
            }
        }

        @Override
        public int compareTo(Vertex other) {
            if (dist == other.dist) {
                return name.compareTo(other.name);
            }
            return Integer.compare(dist, other.dist);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            Vertex vertex = (Vertex) object;

            if (dist != vertex.dist) {
                return false;
            }
            if (name != null ? !name.equals(vertex.name) : vertex.name != null) {
                return false;
            }
            if (previous != null ? !previous.equals(vertex.previous) : vertex.previous != null) {
                return false;
            }
            return neighbours != null ? neighbours.equals(vertex.neighbours) : vertex.neighbours == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + dist;
            result = 31 * result + (previous != null ? previous.hashCode() : 0);
            result = 31 * result + (neighbours != null ? neighbours.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + name + ", " + dist + ")";
        }
    }

    Graph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        for (Edge edge : edges) {
            graph.computeIfAbsent(edge.v1, Vertex::new);
            graph.computeIfAbsent(edge.v2, Vertex::new);
        }

        for (Edge edge : edges) {
            Vertex from = graph.get(edge.v1);
            Vertex to = graph.get(edge.v2);
            from.neighbours.put(to, edge.dist);
            // For an undirected graph, also add the reverse edge:
            // to.neighbours.put(from, edge.dist);
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
            vertex.previous = vertex == source ? source : null;
            vertex.dist = vertex == source ? 0 : Integer.MAX_VALUE;
            queue.add(vertex);
        }

        dijkstra(queue);
    }

    private void dijkstra(final NavigableSet<Vertex> queue) {
        while (!queue.isEmpty()) {
            Vertex u = queue.pollFirst();
            if (u.dist == Integer.MAX_VALUE) {
                break;
            }

            for (Map.Entry<Vertex, Integer> entry : u.neighbours.entrySet()) {
                Vertex v = entry.getKey();
                int alternateDist = u.dist + entry.getValue();

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