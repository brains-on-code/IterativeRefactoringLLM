package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple directed graph implementation that can count its connected components.
 *
 * @param <E> the type of elements stored in the graph nodes
 */
class Graph<E extends Comparable<E>> {

    /** Represents a node in the graph. */
    class Node {

        E value;

        Node(E value) {
            this.value = value;
        }
    }

    /** Represents a directed edge between two nodes in the graph. */
    class Edge {

        Node from;
        Node to;

        Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }
    }

    /** List of all edges in the graph. */
    ArrayList<Edge> edges;

    /** List of all nodes in the graph. */
    ArrayList<Node> nodes;

    Graph() {
        edges = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    /**
     * Adds a directed edge between two values. If the corresponding nodes do not
     * exist yet, they are created and added to the graph.
     *
     * @param from the source value
     * @param to   the destination value
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
     * Counts the number of connected components in the graph, considering
     * reachability via directed edges.
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
     * @param start   the starting node
     * @param visited the list of already visited nodes
     * @return the updated list of visited nodes
     */
    public ArrayList<Node> depthFirstSearch(Node start, ArrayList<Node> visited) {
        visited.add(start);
        for (Edge edge : edges) {
            if (edge.from.equals(start) && !visited.contains(edge.to)) {
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