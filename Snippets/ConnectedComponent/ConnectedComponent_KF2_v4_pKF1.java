package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Graph<E extends Comparable<E>> {

    class Vertex {

        E value;

        Vertex(E value) {
            this.value = value;
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

    List<Edge> edgeList;
    List<Vertex> vertexList;

    Graph() {
        edgeList = new ArrayList<>();
        vertexList = new ArrayList<>();
    }

    public void addEdge(E fromValue, E toValue) {
        Vertex fromVertex = null;
        Vertex toVertex = null;

        for (Vertex vertex : vertexList) {
            if (fromValue.compareTo(vertex.value) == 0) {
                fromVertex = vertex;
            } else if (toValue.compareTo(vertex.value) == 0) {
                toVertex = vertex;
            }
        }

        if (fromVertex == null) {
            fromVertex = new Vertex(fromValue);
            vertexList.add(fromVertex);
        }
        if (toVertex == null) {
            toVertex = new Vertex(toValue);
            vertexList.add(toVertex);
        }

        edgeList.add(new Edge(fromVertex, toVertex));
    }

    public int countConnectedComponents() {
        int connectedComponentCount = 0;
        Set<Vertex> visitedVertices = new HashSet<>();

        for (Vertex vertex : vertexList) {
            if (visitedVertices.add(vertex)) {
                visitedVertices.addAll(depthFirstSearch(vertex, new ArrayList<>()));
                connectedComponentCount++;
            }
        }

        return connectedComponentCount;
    }

    public List<Vertex> depthFirstSearch(Vertex startVertex, List<Vertex> visitedVertices) {
        visitedVertices.add(startVertex);
        for (Edge edge : edgeList) {
            if (edge.from.equals(startVertex) && !visitedVertices.contains(edge.to)) {
                depthFirstSearch(edge.to, visitedVertices);
            }
        }
        return visitedVertices;
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {
    }

    public static void main(String[] args) {
        Graph<Character> characterGraph = new Graph<>();

        characterGraph.addEdge('a', 'b');
        characterGraph.addEdge('a', 'e');
        characterGraph.addEdge('b', 'e');
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