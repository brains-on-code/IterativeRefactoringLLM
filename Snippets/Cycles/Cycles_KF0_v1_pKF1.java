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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of nodes: ");
        nodeCount = scanner.nextInt();
        System.out.print("Enter the number of edges: ");
        final int edgeCount = scanner.nextInt();

        adjacencyMatrix = new int[nodeCount][nodeCount];
        visited = new boolean[nodeCount];

        System.out.println("Enter the details of each edge <Start Node> <End Node>");

        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            int startNode = scanner.nextInt();
            int endNode = scanner.nextInt();
            adjacencyMatrix[startNode][endNode] = 1;
        }
        scanner.close();
    }

    public void findCycles() {
        for (int startNode = 0; startNode < nodeCount; startNode++) {
            List<Integer> currentPath = new ArrayList<>();
            depthFirstSearch(startNode, startNode, currentPath);
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
                adjacencyMatrix[startNode][nodeIndex] = 0;
                adjacencyMatrix[nodeIndex][startNode] = 0;
            }
        }
    }

    private void depthFirstSearch(int startNode, int currentNode, List<Integer> currentPath) {
        currentPath.add(currentNode);
        visited[currentNode] = true;

        for (int neighborNode = 0; neighborNode < nodeCount; neighborNode++) {
            if (adjacencyMatrix[currentNode][neighborNode] == 1) {
                if (neighborNode == startNode) {
                    cycles.add(new ArrayList<>(currentPath));
                } else if (!visited[neighborNode]) {
                    depthFirstSearch(startNode, neighborNode, currentPath);
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
            for (int nodeIndex = 0; nodeIndex < cycle.size(); nodeIndex++) {
                System.out.print(cycle.get(nodeIndex) + " -> ");
            }
            System.out.println(cycle.get(0));
            System.out.println();
        }
    }
}

public final class Cycles {
    private Cycles() {
    }

    public static void main(String[] args) {
        CycleFinder cycleFinder = new CycleFinder();
        cycleFinder.findCycles();
        cycleFinder.printAllCycles();
    }
}