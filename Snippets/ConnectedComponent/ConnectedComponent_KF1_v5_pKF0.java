package com.thealgorithms.datastructures.graphs;

import java.util.*;

/**
 * Represents a generic undirected graph and provides functionality
 * to count its connected components.
 *
 * @param <E> the type of elements stored in the graph nodes
 */
class Graph<E extends Comparable<E>> {

    private static final class Node<E> {
        private final E value;

        private Node(E value) {
            this.value = value;
        }
    }

    private final Map<E, Node<E>> nodeLookup = new HashMap<>();
    private final Map<Node<E>, List<Node<E>>> adjacencyList = new HashMap<>();

    private Node<E> getOrCreateNode(E value) {
        return nodeLookup.computeIfAbsent(value, v -> {
            Node<E> node = new Node<>(v);
            adjacencyList.put(node, new ArrayList<>());
            return node;
        });
    }

    /**
     * Adds an undirected edge between two values. If the corresponding
     * nodes do not exist yet, they are created.
     *
     * @param fromValue the first endpoint of the edge
     * @param toValue   the second endpoint of the edge
     */
    public void addEdge(E fromValue, E toValue) {
        Node<E> fromNode = getOrCreateNode(fromValue);
        Node<E> toNode = getOrCreateNode(toValue);

        adjacencyList.get(fromNode).add(toNode);
        adjacencyList.get(toNode).add(fromNode);
    }

    /**
     * Counts the number of connected components in the graph.
     *
     * @return the number of connected components
     */
    public int countConnectedComponents() {
        int componentCount = 0;
        Set<Node<E>> visited = new HashSet<>();

        for (Node<E> node : adjacencyList.keySet()) {
            if (visited.contains(node)) {
                continue;
            }
            depthFirstSearch(node, visited);
            componentCount++;
        }

        return componentCount;
    }

    /**
     * Performs a depth-first search starting from the given node.
     *
     * @param startNode the node to start the DFS from
     * @param visited   the set of already visited nodes
     */
    private void depthFirstSearch(Node<E> startNode, Set<Node<E>> visited) {
        Deque<Node<E>> stack = new ArrayDeque<>();
        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node<E> current = stack.pop();
            if (!visited.add(current)) {
                continue;
            }

            for (Node<E> neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
    }
}

public final class GraphDemo {

    private GraphDemo() {
        // Utility class
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