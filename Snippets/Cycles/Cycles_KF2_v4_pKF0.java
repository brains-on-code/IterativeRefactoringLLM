package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CycleFinder {

    private final int nodeCount;
    private final int[][] adjacencyMatrix;
    private final boolean[] visited;
    private final List<List<Integer>> cycles;

    CycleFinder(int nodeCount, int edgeCount, int[][] edges) {
        this.nodeCount = nodeCount;
        this.adjacencyMatrix = new int[nodeCount][nodeCount];
        this.visited = new boolean[nodeCount];
        this.cycles = new ArrayList<>();

        initializeAdjacencyMatrix(edgeCount, edges);
    }

    private void initializeAdjacencyMatrix(int edgeCount, int[][] edges) {
        for (int i = 0; i < edgeCount; i++) {
            int startNode = edges[i][0];
            int endNode = edges[i][1];
            adjacencyMatrix[startNode][endNode] = 1;
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
            if (!isEdge(currentNode, neighbor)) {
                continue;
            }

            if (neighbor == startNode) {
                cycles.add(new ArrayList<>(currentPath));
            } else if (!visited[neighbor]) {
                depthFirstSearch(startNode, neighbor, currentPath);
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited[currentNode] = false;
    }

    private boolean isEdge(int from, int to) {
        return adjacencyMatrix[from][to] == 1;
    }

    private void removeIncidentEdges(int node) {
        for (int i = 0; i < nodeCount; i++) {
            adjacencyMatrix[node][i] = 0;
            adjacencyMatrix[i][node] = 0;
        }
    }

    public void printAllCycles() {
        for (List<Integer> cycle : cycles) {
            printCycle(cycle);
        }
    }

    private void printCycle(List<Integer> cycle) {
        for (int node : cycle) {
            System.out.print(node + " -> ");
        }
        System.out.println(cycle.get(0));
        System.out.println();
    }
}

public final class Cycles {

    private Cycles() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int nodeCount = readInt(scanner, "Enter the number of nodes: ");
            int edgeCount = readInt(scanner, "Enter the number of edges: ");

            int[][] edges = readEdges(scanner, edgeCount);

            CycleFinder cycleFinder = new CycleFinder(nodeCount, edgeCount, edges);
            cycleFinder.findCycles();
            cycleFinder.printAllCycles();
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    private static int[][] readEdges(Scanner scanner, int edgeCount) {
        int[][] edges = new int[edgeCount][2];
        System.out.println("Enter the details of each edge: <Start Node> <End Node>");
        for (int i = 0; i < edgeCount; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }
        return edges;
    }
}