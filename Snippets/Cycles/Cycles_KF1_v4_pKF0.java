package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GraphCycleFinder {

    private final int nodeCount;
    private final int[][] adjacencyMatrix;
    private final boolean[] visited;
    private final List<List<Integer>> cycles;

    GraphCycleFinder() {
        try (Scanner scanner = new Scanner(System.in)) {
            this.nodeCount = promptForInt(scanner, "Enter the number of nodes: ");
            int edgeCount = promptForInt(scanner, "Enter the number of edges: ");

            this.adjacencyMatrix = new int[nodeCount][nodeCount];
            this.visited = new boolean[nodeCount];
            this.cycles = new ArrayList<>();

            readEdges(scanner, edgeCount);
        }
    }

    private int promptForInt(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    private void readEdges(Scanner scanner, int edgeCount) {
        System.out.println("Enter each edge as: <Start Node> <End Node>");
        for (int i = 0; i < edgeCount; i++) {
            int startNode = scanner.nextInt();
            int endNode = scanner.nextInt();
            adjacencyMatrix[startNode][endNode] = 1;
        }
    }

    public void findCycles() {
        for (int startNode = 0; startNode < nodeCount; startNode++) {
            List<Integer> currentPath = new ArrayList<>();
            depthFirstSearch(startNode, startNode, currentPath);
            removeNodeConnections(startNode);
        }
    }

    private void depthFirstSearch(int startNode, int currentNode, List<Integer> currentPath) {
        currentPath.add(currentNode);
        visited[currentNode] = true;

        for (int nextNode = 0; nextNode < nodeCount; nextNode++) {
            if (adjacencyMatrix[currentNode][nextNode] != 1) {
                continue;
            }

            if (nextNode == startNode) {
                cycles.add(new ArrayList<>(currentPath));
            } else if (!visited[nextNode]) {
                depthFirstSearch(startNode, nextNode, currentPath);
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited[currentNode] = false;
    }

    private void removeNodeConnections(int node) {
        for (int i = 0; i < nodeCount; i++) {
            adjacencyMatrix[node][i] = 0;
            adjacencyMatrix[i][node] = 0;
        }
    }

    public void printCycles() {
        for (List<Integer> cycle : cycles) {
            printCycle(cycle);
        }
    }

    private void printCycle(List<Integer> cycle) {
        for (int i = 0; i < cycle.size(); i++) {
            int node = cycle.get(i);
            System.out.print(node);
            if (i < cycle.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println(" -> " + cycle.get(0));
        System.out.println();
    }
}

public final class GraphCycleDemo {

    private GraphCycleDemo() {
        // Utility class
    }

    public static void main(String[] args) {
        GraphCycleFinder graphCycleFinder = new GraphCycleFinder();
        graphCycleFinder.findCycles();
        graphCycleFinder.printCycles();
    }
}