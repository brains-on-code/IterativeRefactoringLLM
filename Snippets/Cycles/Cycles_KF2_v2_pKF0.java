package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cycle {

    private final int nodeCount;
    private final int[][] adjacencyMatrix;
    private final boolean[] visited;
    private final List<List<Integer>> cycles = new ArrayList<>();

    Cycle() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of nodes: ");
            nodeCount = scanner.nextInt();

            System.out.print("Enter the number of edges: ");
            int edgeCount = scanner.nextInt();

            adjacencyMatrix = new int[nodeCount][nodeCount];
            visited = new boolean[nodeCount];

            System.out.println("Enter the details of each edge: <Start Node> <End Node>");
            for (int i = 0; i < edgeCount; i++) {
                int startNode = scanner.nextInt();
                int endNode = scanner.nextInt();
                adjacencyMatrix[startNode][endNode] = 1;
            }
        }
    }

    public void findCycles() {
        for (int node = 0; node < nodeCount; node++) {
            List<Integer> currentPath = new ArrayList<>();
            depthFirstSearch(node, node, currentPath);
            removeIncidentEdges(node);
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

        currentPath.remove(currentPath.size() - 1);
        visited[currentNode] = false;
    }

    private void removeIncidentEdges(int node) {
        for (int i = 0; i < nodeCount; i++) {
            adjacencyMatrix[node][i] = 0;
            adjacencyMatrix[i][node] = 0;
        }
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
        Cycle cycleFinder = new Cycle();
        cycleFinder.findCycles();
        cycleFinder.printAllCycles();
    }
}