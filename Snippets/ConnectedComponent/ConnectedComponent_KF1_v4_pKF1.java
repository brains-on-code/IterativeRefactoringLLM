package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Graph<E extends Comparable<E>> {

    class Vertex {

        E data;

        Vertex(E data) {
            this.data = data;
        }
    }

    class Edge {

        Vertex from;
        Vertex to;

        Edge(Vertex from, Vertex to) {
            this.from = from;
            this.to = to;
        }
    }

    List<Edge> edges;
    List<Vertex> vertices;

    Graph() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public void addEdge(E fromValue, E toValue) {
        Vertex fromVertex = null;
        Vertex toVertex = null;

        for (Vertex vertex : vertices) {
            if (fromValue.compareTo(vertex.data) == 0) {
                fromVertex = vertex;
            } else if (toValue.compareTo(vertex.data) == 0) {
                toVertex = vertex;
            }
        }

        if (fromVertex == null) {
            fromVertex = new Vertex(fromValue);
            vertices.add(fromVertex);
        }
        if (toVertex == null) {
            toVertex = new Vertex(toValue);
            vertices.add(toVertex);
        }

        edges.add(new Edge(fromVertex, toVertex));
    }

    public int countConnectedComponents() {
        int connectedComponents = 0;
        Set<Vertex> visitedVertices = new HashSet<>();

        for (Vertex vertex : vertices) {
            if (visitedVertices.add(vertex)) {
                visitedVertices.addAll(depthFirstSearch(vertex, new ArrayList<>()));
                connectedComponents++;
            }
        }

        return connectedComponents;
    }

    public List<Vertex> depthFirstSearch(Vertex startVertex, List<Vertex> visitedVertices) {
        visitedVertices.add(startVertex);
        for (Edge edge : edges) {
            if (edge.from.equals(startVertex) && !visitedVertices.contains(edge.to)) {
                depthFirstSearch(edge.to, visitedVertices);
            }
        }
        return visitedVertices;
    }
}

public final class GraphDemo {
    private GraphDemo() {
    }

    public static void main(String[] args) {
        Graph<Character> characterGraph = new Graph<>();

        characterGraph.addEdge('a', 'b');
        characterGraph.addEdge('a', 'v');
        characterGraph.addEdge('b', 'v');
        characterGraph.addEdge('b', 'c');
        characterGraph.addEdge('c', 'd');
        characterGraph.addEdge('d', 'a');

        characterGraph.addEdge('x', 'y');
        characterGraph.addEdge('x', 'z');

        characterGraph.addEdge('w', 'w');

        Graph<Integer> integerGraph = new Graph<>();

        integerGraph.addEdge(1, 2);
        integerGraph.addEdge(2, 3);
        integerGraph.addEdge(2, 4);
        integerGraph.addEdge(3, 5);

        integerGraph.addEdge(7, 8);
        integerGraph.addEdge(8, 10);
        integerGraph.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + characterGraph.countConnectedComponents());
        System.out.println("Amount of different int-graphs: " + integerGraph.countConnectedComponents());
    }
}