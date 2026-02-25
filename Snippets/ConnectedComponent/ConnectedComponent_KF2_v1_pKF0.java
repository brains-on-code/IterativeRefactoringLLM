package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Graph<E extends Comparable<E>> {

    class Node {
        final E name;

        Node(E name) {
            this.name = name;
        }
    }

    class Edge {
        final Node startNode;
        final Node endNode;

        Edge(Node startNode, Node endNode) {
            this.startNode = startNode;
            this.endNode = endNode;
        }
    }

    private final List<Edge> edgeList;
    private final List<Node> nodeList;

    Graph() {
        this.edgeList = new ArrayList<>();
        this.nodeList = new ArrayList<>();
    }

    public void addEdge(E startValue, E endValue) {
        Node startNode = findOrCreateNode(startValue);
        Node endNode = findOrCreateNode(endValue);
        edgeList.add(new Edge(startNode, endNode));
    }

    private Node findOrCreateNode(E value) {
        for (Node node : nodeList) {
            if (value.compareTo(node.name) == 0) {
                return node;
            }
        }
        Node newNode = new Node(value);
        nodeList.add(newNode);
        return newNode;
    }

    public int countGraphs() {
        int componentCount = 0;
        Set<Node> visitedNodes = new HashSet<>();

        for (Node node : nodeList) {
            if (visitedNodes.add(node)) {
                visitedNodes.addAll(depthFirstSearch(node, new ArrayList<>()));
                componentCount++;
            }
        }

        return componentCount;
    }

    public List<Node> depthFirstSearch(Node node, List<Node> visited) {
        visited.add(node);
        for (Edge edge : edgeList) {
            if (edge.startNode.equals(node) && !visited.contains(edge.endNode)) {
                depthFirstSearch(edge.endNode, visited);
            }
        }
        return visited;
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {}

    public static void main(String[] args) {
        Graph<Character> graphChars = new Graph<>();

        graphChars.addEdge('a', 'b');
        graphChars.addEdge('a', 'e');
        graphChars.addEdge('b', 'e');
        graphChars.addEdge('b', 'c');
        graphChars.addEdge('c', 'd');
        graphChars.addEdge('d', 'a');

        graphChars.addEdge('x', 'y');
        graphChars.addEdge('x', 'z');

        graphChars.addEdge('w', 'w');

        Graph<Integer> graphInts = new Graph<>();

        graphInts.addEdge(1, 2);
        graphInts.addEdge(2, 3);
        graphInts.addEdge(2, 4);
        graphInts.addEdge(3, 5);

        graphInts.addEdge(7, 8);
        graphInts.addEdge(8, 10);
        graphInts.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + graphChars.countGraphs());
        System.out.println("Amount of different int-graphs: " + graphInts.countGraphs());
    }
}