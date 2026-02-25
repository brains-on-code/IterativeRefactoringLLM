package com.thealgorithms.datastructures.graphs;

import java.util.*;

/**
 * A class that counts the number of different connected components in a graph
 *
 * @author Lukas Keul, Florian Mercks
 */
class Graph<E extends Comparable<E>> {

    private class Node {
        final E value;

        Node(E value) {
            this.value = value;
        }
    }

    private class Edge {
        final Node start;
        final Node end;

        Edge(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    private final Map<E, Node> nodeMap;
    private final Map<Node, List<Node>> adjacencyList;

    Graph() {
        this.nodeMap = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a new Edge to the graph. If the nodes aren't yet in nodeMap, they
     * will be added to it.
     *
     * @param startValue the starting node value of the edge
     * @param endValue   the ending node value of the edge
     */
    public void addEdge(E startValue, E endValue) {
        Node startNode = findOrCreateNode(startValue);
        Node endNode = findOrCreateNode(endValue);

        adjacencyList.computeIfAbsent(startNode, k -> new ArrayList<>()).add(endNode);
        adjacencyList.computeIfAbsent(endNode, k -> new ArrayList<>());
    }

    private Node findOrCreateNode(E value) {
        return nodeMap.computeIfAbsent(value, v -> {
            Node newNode = new Node(v);
            adjacencyList.put(newNode, new ArrayList<>());
            return newNode;
        });
    }

    /**
     * Counts the number of connected components in the graph.
     *
     * @return the number of connected components
     */
    public int countGraphs() {
        int componentCount = 0;
        Set<Node> visitedNodes = new HashSet<>();

        for (Node node : adjacencyList.keySet()) {
            if (!visitedNodes.contains(node)) {
                depthFirstSearch(node, visitedNodes);
                componentCount++;
            }
        }

        return componentCount;
    }

    /**
     * Implementation of depth first search.
     *
     * @param node         the current visiting node
     * @param visitedNodes a set of already visited nodes in the depth first search
     */
    private void depthFirstSearch(Node node, Set<Node> visitedNodes) {
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (visitedNodes.add(current)) {
                for (Node neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                    if (!visitedNodes.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
}

public final class ConnectedComponent {
    private ConnectedComponent() {}

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