package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Graph<E extends Comparable<E>> {

    class Node {
        E value;

        Node(E value) {
            this.value = value;
        }
    }

    class Edge {
        Node from;
        Node to;

        Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }
    }

    private final List<Edge> edges;
    private final List<Node> nodes;

    Graph() {
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public void addEdge(E fromValue, E toValue) {
        Node fromNode = null;
        Node toNode = null;

        for (Node node : nodes) {
            if (fromValue.compareTo(node.value) == 0) {
                fromNode = node;
            } else if (toValue.compareTo(node.value) == 0) {
                toNode = node;
            }
        }

        if (fromNode == null) {
            fromNode = new Node(fromValue);
            nodes.add(fromNode);
        }
        if (toNode == null) {
            toNode = new Node(toValue);
            nodes.add(toNode);
        }

        edges.add(new Edge(fromNode, toNode));
    }

    public int countConnectedComponents() {
        int componentCount = 0;
        Set<Node> visitedNodes = new HashSet<>();

        for (Node node : nodes) {
            if (visitedNodes.add(node)) {
                visitedNodes.addAll(depthFirstSearch(node, new ArrayList<>()));
                componentCount++;
            }
        }

        return componentCount;
    }

    public List<Node> depthFirstSearch(Node start, List<Node> visited) {
        visited.add(start);
        for (Edge edge : edges) {
            if (edge.from.equals(start) && !visited.contains(edge.to)) {
                depthFirstSearch(edge.to, visited);
            }
        }
        return visited;
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {}

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