package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GraphCycleFinder {

    private final int nodeCount;
    private int[][] adjacencyMatrix;
    private boolean[] visited;
    private final List<List<Integer>> cycles = new ArrayList<>();

    GraphCycleFinder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the no. of nodes: ");
        nodeCount = scanner.nextInt();
        System.out.print("Enter the no. of Edges: ");
        final int edgeCount = scanner.nextInt();

        adjacencyMatrix = new int[nodeCount][nodeCount];
        visited = new boolean[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            visited[i] = false;
        }

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
            ArrayList<Integer> currentPath = new ArrayList<>();
            depthFirstSearch(startNode, startNode, currentPath);
            for (int i = 0; i < nodeCount; i++) {
                adjacencyMatrix[startNode][i] = 0;
                adjacencyMatrix[i][startNode] = 0;
            }
        }
    }

    private void depthFirstSearch(Integer startNode, Integer currentNode, ArrayList<Integer> currentPath) {
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

    public void printCycles() {
        for (int i = 0; i < cycles.size(); i++) {
            List<Integer> cycle = cycles.get(i);
            for (int j = 0; j < cycle.size(); j++) {
                System.out.print(cycle.get(j) + " -> ");
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