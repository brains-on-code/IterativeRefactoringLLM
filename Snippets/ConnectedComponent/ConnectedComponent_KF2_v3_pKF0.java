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
        private final Node from;
        private final Node to;

        private Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }
    }

    private final List<Edge> edges = new ArrayList<>();
    private final List<Node> nodes = new ArrayList<>();

    public void addEdge(E fromValue, E toValue) {
        Node fromNode = findOrCreateNode(fromValue);
        Node toNode = findOrCreateNode(toValue);
        edges.add(new Edge(fromNode, toNode));
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

    private void depthFirstSearch(Node current, Set<Node> visited) {
        visited.add(current);
        for (Edge edge : edges) {
            if (edge.from == current && !visited.contains(edge.to)) {
                depthFirstSearch(edge.to, visited);
            }
        }
    }
}

public final class ConnectedComponent {

    private ConnectedComponent() {
        // Utility class
    }

    public static void main(String[] args) {
        Graph<Character> charGraph = new Graph<>();

        charGraph.addEdge('a', 'b');
        charGraph.addEdge('a', 'e');
        charGraph.addEdge('b', 'e');
        charGraph.addEdge('b', 'c');
        charGraph.addEdge('c', 'd');
        charGraph.addEdge('d', 'a');

        charGraph.addEdge('x', 'y');
        charGraph.addEdge('x', 'z');

        charGraph.addEdge('w', 'w');

        Graph<Integer> intGraph = new Graph<>();

        intGraph.addEdge(1, 2);
        intGraph.addEdge(2, 3);
        intGraph.addEdge(2, 4);
        intGraph.addEdge(3, 5);

        intGraph.addEdge(7, 8);
        intGraph.addEdge(8, 10);
        intGraph.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + charGraph.countConnectedComponents());
        System.out.println("Amount of different int-graphs: " + intGraph.countConnectedComponents());
    }
}