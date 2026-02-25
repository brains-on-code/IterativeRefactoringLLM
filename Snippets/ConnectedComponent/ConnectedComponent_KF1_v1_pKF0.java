package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a generic undirected graph and provides functionality
 * to count its connected components.
 *
 * @param <E> the type of elements stored in the graph nodes
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
     * Adds an undirected edge between two values. If the corresponding
     * nodes do not exist yet, they are created.
     *
     * @param fromValue the first endpoint of the edge
     * @param toValue   the second endpoint of the edge
     */
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

    /**
     * Counts the number of connected components in the graph.
     *
     * @return the number of connected components
     */
    public int countConnectedComponents() {
        int componentCount = 0;
        Set<Node> visited = new HashSet<>();

        for (Node node : nodes) {
            if (visited.add(node)) {
                visited.addAll(depthFirstSearch(node, new ArrayList<>()));
                componentCount++;
            }
        }

        return componentCount;
    }

    /**
     * Performs a depth-first search starting from the given node.
     *
     * @param startNode the node to start the DFS from
     * @param visited   the list of already visited nodes
     * @return the list of visited nodes after the DFS
     */
    public List<Node> depthFirstSearch(Node startNode, List<Node> visited) {
        visited.add(startNode);
        for (Edge edge : edges) {
            if (edge.from.equals(startNode) && !visited.contains(edge.to)) {
                depthFirstSearch(edge.to, visited);
            }
        }
        return visited;
    }
}

public final class GraphDemo {

    private GraphDemo() {
    }

    public static void main(String[] args) {
        Graph<Character> charGraph = new Graph<>();

        // First character graph
        charGraph.addEdge('a', 'b');
        charGraph.addEdge('a', 'v');
        charGraph.addEdge('b', 'v');
        charGraph.addEdge('b', 'c');
        charGraph.addEdge('c', 'd');
        charGraph.addEdge('d', 'a');

        // Second character graph
        charGraph.addEdge('x', 'y');
        charGraph.addEdge('x', 'z');

        // Third character graph (self-loop)
        charGraph.addEdge('w', 'w');

        Graph<Integer> intGraph = new Graph<>();

        // First integer graph
        intGraph.addEdge(1, 2);
        intGraph.addEdge(2, 3);
        intGraph.addEdge(2, 4);
        intGraph.addEdge(3, 5);

        // Second integer graph
        intGraph.addEdge(7, 8);
        intGraph.addEdge(8, 10);
        intGraph.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + charGraph.countConnectedComponents());
        System.out.println("Amount of different int-graphs: " + intGraph.countConnectedComponents());
    }
}