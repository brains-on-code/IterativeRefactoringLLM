package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Counts the number of connected components in a graph.
 *
 * @author Lukas Keul, Florian Mercks
 */
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

    /**
     * Adds a new edge to the graph. If either endpoint is not yet present
     * in the node list, it is created and added.
     *
     * @param from the start node value
     * @param to   the end node value
     */
    public void addEdge(E from, E to) {
        Node fromNode = findOrCreateNode(from);
        Node toNode = findOrCreateNode(to);
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

    /**
     * Counts the number of connected components in the graph.
     *
     * @return number of connected components
     */
    public int countGraphs() {
        int components = 0;
        Set<Node> visited = new HashSet<>();

        for (Node node : nodes) {
            if (visited.add(node)) {
                depthFirstSearch(node, visited);
                components++;
            }
        }

        return components;
    }

    /**
     * Performs a depth-first search starting from the given node.
     *
     * @param node    starting node
     * @param visited nodes visited so far
     */
    private void depthFirstSearch(Node node, Set<Node> visited) {
        for (Edge edge : edges) {
            if (edge.from.equals(node) && visited.add(edge.to)) {
                depthFirstSearch(edge.to, visited);
            }
        }
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