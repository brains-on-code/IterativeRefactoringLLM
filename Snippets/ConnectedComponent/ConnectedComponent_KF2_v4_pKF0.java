package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Graph<E extends Comparable<E>> {

    private class Node {
        private final E value;

        private Node(E value) {
            this.value = value;
        }
    }

    private class Edge {
        private final Node source;
        private final Node destination;

        private Edge(Node source, Node destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    private final List<Edge> edges = new ArrayList<>();
    private final List<Node> nodes = new ArrayList<>();

    public void addEdge(E sourceValue, E destinationValue) {
        Node sourceNode = findOrCreateNode(sourceValue);
        Node destinationNode = findOrCreateNode(destinationValue);
        edges.add(new Edge(sourceNode, destinationNode));
    }

    private Node findOrCreateNode(E value) {
        for (Node node : nodes) {
            if (value.compareTo(node.value) == 0) {
                return node;
            }
        }
        Node newNode = new Node(value);
        nodes.add(newNode);
        return newNode;
    }

    public int countConnectedComponents() {
        int componentCount = 0;
        Set<Node> visitedNodes = new HashSet<>();

        for (Node node : nodes) {
            if (!visitedNodes.contains(node)) {
                depthFirstSearch(node, visitedNodes);
                componentCount++;
            }
        }

        return componentCount;
    }

    private void depthFirstSearch(Node currentNode, Set<Node> visitedNodes) {
        visitedNodes.add(currentNode);
        for (Edge edge : edges) {
            if (edge.source == currentNode && !visitedNodes.contains(edge.destination)) {
                depthFirstSearch(edge.destination, visitedNodes);
            }
        }
    }
}

public final class ConnectedComponent {

    private ConnectedComponent() {
        // Utility class
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

        System.out.println("Number of connected components in character graph: " + characterGraph.countConnectedComponents());
        System.out.println("Number of connected components in integer graph: " + integerGraph.countConnectedComponents());
    }
}