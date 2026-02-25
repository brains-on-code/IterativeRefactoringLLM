package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CycleFinder {

    private final int nodeCount;
    private final int[][] adjacencyMatrix;
    private final boolean[] visited;
    private final List<List<Integer>> cycles = new ArrayList<>();

    CycleFinder() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of nodes: ");
            nodeCount = scanner.nextInt();

            System.out.print("Enter the number of edges: ");
            int edgeCount = scanner.nextInt();

            adjacencyMatrix = new int[nodeCount][nodeCount];
            visited = new boolean[nodeCount];

            System.out.println("Enter each edge as: <startNode> <endNode>");
            for (int i = 0; i < edgeCount; i++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                adjacencyMatrix[start][end] = 1;
            }
        }
    }

    public void findCycles() {
        for (int i = 0; i < nodeCount; i++) {
            List<Integer> currentPath = new ArrayList<>();
            depthFirstSearch(i, i, currentPath);

            for (int j = 0; j < nodeCount; j++) {
                adjacencyMatrix[i][j] = 0;
                adjacencyMatrix[j][i] = 0;
            }
        }
    }

    private void depthFirstSearch(int startNode, int currentNode, List<Integer> currentPath) {
        currentPath.add(currentNode);
        visited[currentNode] = true;

        for (int neighbor = 0; neighbor < nodeCount; neighbor++) {
            if (adjacencyMatrix[currentNode][neighbor] == 1) {
                if (neighbor == startNode) {
                    cycles.add(new ArrayList<>(currentPath));
                } else if (!visited[neighbor]) {
                    depthFirstSearch(startNode, neighbor, currentPath);
                }
            }
        }

        if (!currentPath.isEmpty()) {
            currentPath.remove(currentPath.size() - 1);
        }
        visited[currentNode] = false;
    }

    public void printAllCycles() {
        for (List<Integer> cycle : cycles) {
            for (int node : cycle) {
                System.out.print(node + " -> ");
            }
            System.out.println(cycle.get(0));
            System.out.println();
        }
    }
}

public final class Cycles {

    private Cycles() {
        // Utility class
    }

    public static void main(String[] args) {
        CycleFinder cycleFinder = new CycleFinder();
        cycleFinder.findCycles();
        cycleFinder.printAllCycles();
    }
}