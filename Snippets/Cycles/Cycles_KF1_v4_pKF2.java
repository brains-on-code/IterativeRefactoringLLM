package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Finds and prints all simple cycles in a directed graph using DFS on an
 * adjacency matrix.
 */
class GraphCycleFinder {

    private final int nodeCount;
    private final int[][] adjacencyMatrix;
    private final boolean[] visited;
    private final List<List<Integer>> cycles = new ArrayList<>();

    GraphCycleFinder() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of nodes: ");
            nodeCount = scanner.nextInt();

            System.out.print("Enter the number of edges: ");
            int edgeCount = scanner.nextInt();

            adjacencyMatrix = new int[nodeCount][nodeCount];
            visited = new boolean[nodeCount];

            System.out.println("Enter each edge as: <Start Node> <End Node>");
            for (int i = 0; i < edgeCount; i++) {
                int startNode = scanner.nextInt();
                int endNode = scanner.nextInt();
                adjacencyMatrix[startNode][endNode] = 1;
            }
        }
    }

    /**
     * Finds all simple cycles in the graph.
     */
    public void findCycles() {
        for (int startNode = 0; startNode < nodeCount; startNode++) {
            List<Integer> currentPath = new ArrayList<>();
            depthFirstSearch(startNode, startNode, currentPath);

            // Remove all edges incident to this start node to avoid
            // reporting the same cycle multiple times with different
            // starting points.
            for (int node = 0; node < nodeCount; node++) {
                adjacencyMatrix[startNode][node] = 0;
                adjacencyMatrix[node][startNode] = 0;
            }
        }
    }

    /**
     * Performs DFS to find cycles that start and end at {@code startNode}.
     *
     * @param startNode   the node where the cycle must start and end
     * @param currentNode the node currently being explored
     * @param currentPath the path from {@code startNode} to {@code currentNode}
     */
    private void depthFirstSearch(int startNode, int currentNode, List<Integer> currentPath) {
        currentPath.add(currentNode);
        visited[currentNode] = true;

        for (int nextNode = 0; nextNode < nodeCount; nextNode++) {
            if (adjacencyMatrix[currentNode][nextNode] != 1) {
                continue;
            }

            if (nextNode == startNode) {
                // A cycle is found; record the current path as a cycle.
                cycles.add(new ArrayList<>(currentPath));
            } else if (!visited[nextNode]) {
                depthFirstSearch(startNode, nextNode, currentPath);
            }
        }

        // Backtrack: remove the current node from the path and mark it unvisited.
        if (!currentPath.isEmpty()) {
            currentPath.remove(currentPath.size() - 1);
        }
        visited[currentNode] = false;
    }

    /**
     * Prints all discovered cycles.
     */
    public void printCycles() {
        for (List<Integer> cycle : cycles) {
            for (int node : cycle) {
                System.out.print(node + " -> ");
            }
            System.out.println(cycle.get(0));
            System.out.println();
        }
    }
}

public final class GraphCycleFinderDemo {

    private GraphCycleFinderDemo() {
        // Prevent instantiation.
    }

    public static void main(String[] args) {
        GraphCycleFinder graphCycleFinder = new GraphCycleFinder();
        graphCycleFinder.findCycles();
        graphCycleFinder.printCycles();
    }
}