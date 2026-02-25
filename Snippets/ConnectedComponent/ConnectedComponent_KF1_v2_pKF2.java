package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple directed graph implementation that can count its connected components.
 *
 * @param <E> the type of elements stored in the graph nodes
 */
class Graph<E extends Comparable<E>> {

    /**
     * Graph node holding a value.
     */
    class Node {
        final E value;

        Node(E value) {
            this.value = value;
        }
    }

    /**
     * Directed edge from one node to another.
     */
    class Edge {
        final Node from;
        final Node to;

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
     * Adds a directed edge between two values. Missing nodes are created.
     *
     * @param from source value
     * @param to   destination value
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
     * Counts connected components based on reachability via directed edges.
     *
     * @return number of connected components
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
     * Depth-first search starting from a node.
     *
     * @param start   starting node
     * @param visited already visited nodes
     * @return updated list of visited nodes
     */
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

public final class GraphDemo {
    private GraphDemo() {
    }

    public static void main(String[] args) {
        Graph<Character> charGraph = new Graph<>();

        // Component 1 (characters)
        charGraph.addEdge('a', 'b');
        charGraph.addEdge('a', 'v');
        charGraph.addEdge('b', 'v');
        charGraph.addEdge('b', 'c');
        charGraph.addEdge('c', 'd');
        charGraph.addEdge('d', 'a');

        // Component 2 (characters)
        charGraph.addEdge('x', 'y');
        charGraph.addEdge('x', 'z');

        // Component 3 (character self-loop)
        charGraph.addEdge('w', 'w');

        Graph<Integer> intGraph = new Graph<>();

        // Component 1 (integers)
        intGraph.addEdge(1, 2);
        intGraph.addEdge(2, 3);
        intGraph.addEdge(2, 4);
        intGraph.addEdge(3, 5);

        // Component 2 (integers)
        intGraph.addEdge(7, 8);
        intGraph.addEdge(8, 10);
        intGraph.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + charGraph.countConnectedComponents());
        System.out.println("Amount of different int-graphs: " + intGraph.countConnectedComponents());
    }
}