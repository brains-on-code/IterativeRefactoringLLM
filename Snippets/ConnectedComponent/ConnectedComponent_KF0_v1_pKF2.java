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
        Node fromNode = null;
        Node toNode = null;

        for (Node node : nodes) {
            if (from.compareTo(node.value) == 0) {
                fromNode = node;
            } else if (to.compareTo(node.value) == 0) {
                toNode = node;
            }
        }

        if (fromNode == null) {
            fromNode = new Node(from);
            nodes.add(fromNode);
        }
        if (toNode == null) {
            toNode = new Node(to);
            nodes.add(toNode);
        }

        edges.add(new Edge(fromNode, toNode));
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
                visited.addAll(depthFirstSearch(node, new ArrayList<>()));
                components++;
            }
        }

        return components;
    }

    /**
     * Depth-first search starting from the given node.
     *
     * @param node    starting node
     * @param visited nodes visited so far
     * @return list of all visited nodes reachable from {@code node}
     */
    public List<Node> depthFirstSearch(Node node, List<Node> visited) {
        visited.add(node);
        for (Edge edge : edges) {
            if (edge.from.equals(node) && !visited.contains(edge.to)) {
                depthFirstSearch(edge.to, visited);
            }
        }
        return visited;
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {}

    public static void main(String[] args) {
        Graph<Character> graphChars = new Graph<>();

        // First character graph
        graphChars.addEdge('a', 'b');
        graphChars.addEdge('a', 'e');
        graphChars.addEdge('b', 'e');
        graphChars.addEdge('b', 'c');
        graphChars.addEdge('c', 'd');
        graphChars.addEdge('d', 'a');

        // Second character graph
        graphChars.addEdge('x', 'y');
        graphChars.addEdge('x', 'z');

        // Third character graph (self-loop)
        graphChars.addEdge('w', 'w');

        Graph<Integer> graphInts = new Graph<>();

        // First integer graph
        graphInts.addEdge(1, 2);
        graphInts.addEdge(2, 3);
        graphInts.addEdge(2, 4);
        graphInts.addEdge(3, 5);

        // Second integer graph
        graphInts.addEdge(7, 8);
        graphInts.addEdge(8, 10);
        graphInts.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + graphChars.countGraphs());
        System.out.println("Amount of different int-graphs: " + graphInts.countGraphs());
    }
}