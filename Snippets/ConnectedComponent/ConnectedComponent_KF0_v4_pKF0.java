package com.thealgorithms.datastructures.graphs;

import java.util.*;

/**
 * A class that counts the number of different connected components in a graph.
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

    private final Map<E, Node> valueToNode;
    private final Map<Node, List<Node>> adjacencyList;

    Graph() {
        this.valueToNode = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a new edge to the graph. If the nodes aren't yet in the graph, they
     * will be created and added.
     *
     * @param startValue the starting node value of the edge
     * @param endValue   the ending node value of the edge
     */
    public void addEdge(E startValue, E endValue) {
        Node startNode = getOrCreateNode(startValue);
        Node endNode = getOrCreateNode(endValue);

        adjacencyList.computeIfAbsent(startNode, k -> new ArrayList<>()).add(endNode);
        adjacencyList.computeIfAbsent(endNode, k -> new ArrayList<>());
    }

    private Node getOrCreateNode(E value) {
        return valueToNode.computeIfAbsent(value, v -> {
            Node newNode = new Node(v);
            adjacencyList.putIfAbsent(newNode, new ArrayList<>());
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
            if (visitedNodes.contains(node)) {
                continue;
            }
            depthFirstSearch(node, visitedNodes);
            componentCount++;
        }

        return componentCount;
    }

    /**
     * Iterative depth-first search.
     *
     * @param startNode    the starting node for DFS
     * @param visitedNodes a set of already visited nodes in the depth first search
     */
    private void depthFirstSearch(Node startNode, Set<Node> visitedNodes) {
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (!visitedNodes.add(current)) {
                continue;
            }

            for (Node neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!visitedNodes.contains(neighbor)) {
                    stack.push(neighbor);
                }
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

        // Graph 1
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

        // Graph 2
        intGraph.addEdge(1, 2);
        intGraph.addEdge(2, 3);
        intGraph.addEdge(2, 4);
        intGraph.addEdge(3, 5);

        intGraph.addEdge(7, 8);
        intGraph.addEdge(8, 10);
        intGraph.addEdge(10, 8);

        System.out.println("Amount of different char-graphs: " + charGraph.countGraphs());
        System.out.println("Amount of different int-graphs: " + intGraph.countGraphs());
    }
}