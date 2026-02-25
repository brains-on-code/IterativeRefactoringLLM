package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GraphCycleFinder {

    private final int nodeCount;
    private final int[][] adjacencyMatrix;
    private final boolean[] visited;
    private final List<List<Integer>> cycles = new ArrayList<>();

    GraphCycleFinder() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the no. of nodes: ");
        nodeCount = scanner.nextInt();

        System.out.print("Enter the no. of Edges: ");
        int edgeCount = scanner.nextInt();

        adjacencyMatrix = new int[nodeCount][nodeCount];
        visited = new boolean[nodeCount];

        System.out.println("Enter the details of each edges <Start Node> <End Node>");

        for (int i = 0; i < edgeCount; i++) {
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

            for (int i = 0; i < nodeCount; i++) {
                adjacencyMatrix[startNode][i] = 0;
                adjacencyMatrix[i][startNode] = 0;
            }
        }
    }

    private void depthFirstSearch(int startNode, int currentNode, List<Integer> currentPath) {
        currentPath.add(currentNode);
        visited[currentNode] = true;

        for (int nextNode = 0; nextNode < nodeCount; nextNode++) {
            if (adjacencyMatrix[currentNode][nextNode] == 1) {
                if (nextNode == startNode) {
                    cycles.add(new ArrayList<>(currentPath));
                } else if (!visited[nextNode]) {
                    depthFirstSearch(startNode, nextNode, currentPath);
                }
            }
        }

        if (!currentPath.isEmpty()) {
            currentPath.remove(currentPath.size() - 1);
        }
        visited[currentNode] = false;
    }

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

public final class GraphCycleDemo {

    private GraphCycleDemo() {
    }

    public static void main(String[] args) {
        GraphCycleFinder graphCycleFinder = new GraphCycleFinder();
        graphCycleFinder.findCycles();
        graphCycleFinder.printCycles();
    }
}