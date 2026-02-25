package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that counts the number of different connected components in a graph
 *
 * @author Lukas Keul, Florian Mercks
 */
class Graph<E extends Comparable<E>> {

    class Node {
        final E value;

        Node(E value) {
            this.value = value;
        }
    }

    class Edge {
        final Node start;
        final Node end;

        Edge(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    private final List<Edge> edges;
    private final List<Node> nodes;

    Graph() {
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    /**
     * Adds a new Edge to the graph. If the nodes aren't yet in nodeList, they
     * will be added to it.
     *
     * @param startValue the starting node value of the edge
     * @param endValue   the ending node value of the edge
     */
    public void addEdge(E startValue, E endValue) {
        Node startNode = findOrCreateNode(startValue);
        Node endNode = findOrCreateNode(endValue);
        edges.add(new Edge(startNode, endNode));
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
     * @return the number of connected components
     */
    public int countGraphs() {
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

    /**
     * Implementation of depth first search.
     *
     * @param node    the current visiting node
     * @param visited a list of already visited nodes in the depth first search
     * @return a list of visited nodes
     */
    public List<Node> depthFirstSearch(Node node, List<Node> visited) {
        visited.add(node);
        for (Edge edge : edges) {
            if (edge.start.equals(node) && !visited.contains(edge.end)) {
                depthFirstSearch(edge.end, visited);
            }
        }
        return visited;
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {
    }

    public static void main(String[] args) {
        Graph<Character> graphChars = new Graph<>();

        // Graph 1
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

        // Graph 2
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