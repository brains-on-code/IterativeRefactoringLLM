package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple directed graph implementation using adjacency lists.
 *
 * @param <E> the type of elements stored in the graph vertices
 */
class Graph<E extends Comparable<E>> {

    private final List<Vertex> vertices;

    Graph() {
        this.vertices = new ArrayList<>();
    }

    /**
     * Represents a vertex in the graph.
     */
    private class Vertex {

        E value;
        List<Vertex> adjacentVertices;

        Vertex(E value) {
            this.value = value;
            this.adjacentVertices = new ArrayList<>();
        }

        /**
         * Adds a directed edge from this vertex to the given vertex if it does not already exist.
         *
         * @param target the vertex to connect to
         * @return true if the edge was added, false if it already existed
         */
        public boolean addEdge(Vertex target) {
            for (Vertex vertex : adjacentVertices) {
                if (vertex.value.compareTo(target.value) == 0) {
                    return false;
                }
            }
            return adjacentVertices.add(target);
        }

        /**
         * Removes a directed edge from this vertex to the vertex with the given value.
         *
         * @param targetValue the value of the vertex to disconnect from
         * @return true if the edge was removed, false if it did not exist
         */
        public boolean removeEdge(E targetValue) {
            for (int i = 0; i < adjacentVertices.size(); i++) {
                if (adjacentVertices.get(i).value.compareTo(targetValue) == 0) {
                    adjacentVertices.remove(i);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Removes a directed edge from the vertex with value {@code from} to the vertex with value {@code to}.
     *
     * @param from the source vertex value
     * @param to   the target vertex value
     * @return true if the edge was removed, false if the source vertex or edge does not exist
     */
    public boolean removeEdge(E from, E to) {
        Vertex source = null;
        for (Vertex vertex : vertices) {
            if (from.compareTo(vertex.value) == 0) {
                source = vertex;
                break;
            }
        }
        if (source == null) {
            return false;
        }
        return source.removeEdge(to);
    }

    /**
     * Adds a directed edge from the vertex with value {@code from} to the vertex with value {@code to}.
     * If either vertex does not exist, it is created.
     *
     * @param from the source vertex value
     * @param to   the target vertex value
     * @return true if the edge was added, false if it already existed
     */
    public boolean addEdge(E from, E to) {
        Vertex source = null;
        Vertex target = null;

        for (Vertex vertex : vertices) {
            if (from.compareTo(vertex.value) == 0) {
                source = vertex;
            } else if (to.compareTo(vertex.value) == 0) {
                target = vertex;
            }
            if (source != null && target != null) {
                break;
            }
        }

        if (source == null) {
            source = new Vertex(from);
            vertices.add(source);
        }
        if (target == null) {
            target = new Vertex(to);
            vertices.add(target);
        }

        return source.addEdge(target);
    }

    /**
     * Returns a string representation of the graph, listing each vertex and its adjacent vertices.
     *
     * @return a string representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Vertex vertex : vertices) {
            builder.append("Vertex: ");
            builder.append(vertex.value);
            builder.append("\n");
            builder.append("Adjacent vertices: ");
            for (Vertex adjacent : vertex.adjacentVertices) {
                builder.append(adjacent.value);
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
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