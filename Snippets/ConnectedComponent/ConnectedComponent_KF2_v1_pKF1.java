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

        Vertex source;
        Vertex destination;

        Edge(Vertex source, Vertex destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    List<Edge> edges;
    List<Vertex> vertices;

    Graph() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public void addEdge(E sourceValue, E destinationValue) {
        Vertex sourceVertex = null;
        Vertex destinationVertex = null;

        for (Vertex vertex : vertices) {
            if (sourceValue.compareTo(vertex.value) == 0) {
                sourceVertex = vertex;
            } else if (destinationValue.compareTo(vertex.value) == 0) {
                destinationVertex = vertex;
            }
        }

        if (sourceVertex == null) {
            sourceVertex = new Vertex(sourceValue);
            vertices.add(sourceVertex);
        }
        if (destinationVertex == null) {
            destinationVertex = new Vertex(destinationValue);
            vertices.add(destinationVertex);
        }

        edges.add(new Edge(sourceVertex, destinationVertex));
    }

    public int countConnectedComponents() {
        int componentCount = 0;
        Set<Vertex> visitedVertices = new HashSet<>();

        for (Vertex vertex : vertices) {
            if (visitedVertices.add(vertex)) {
                visitedVertices.addAll(depthFirstSearch(vertex, new ArrayList<>()));
                componentCount++;
            }
        }

        return componentCount;
    }

    public List<Vertex> depthFirstSearch(Vertex currentVertex, List<Vertex> visitedVertices) {
        visitedVertices.add(currentVertex);
        for (Edge edge : edges) {
            if (edge.source.equals(currentVertex) && !visitedVertices.contains(edge.destination)) {
                depthFirstSearch(edge.destination, visitedVertices);
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