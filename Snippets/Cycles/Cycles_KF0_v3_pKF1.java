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

        for (int i = 0; i < edgeCount; i++) {
            int fromNode = scanner.nextInt();
            int toNode = scanner.nextInt();
            adjacencyMatrix[fromNode][toNode] = 1;
        }
        scanner.close();
    }

    public void findCycles() {
        for (int startNode = 0; startNode < nodeCount; startNode++) {
            List<Integer> path = new ArrayList<>();
            depthFirstSearch(startNode, startNode, path);
            for (int node = 0; node < nodeCount; node++) {
                adjacencyMatrix[startNode][node] = 0;
                adjacencyMatrix[node][startNode] = 0;
            }
        }
    }

    private void depthFirstSearch(int startNode, int currentNode, List<Integer> path) {
        path.add(currentNode);
        visited[currentNode] = true;

        for (int neighbor = 0; neighbor < nodeCount; neighbor++) {
            if (adjacencyMatrix[currentNode][neighbor] == 1) {
                if (neighbor == startNode) {
                    cycles.add(new ArrayList<>(path));
                } else if (!visited[neighbor]) {
                    depthFirstSearch(startNode, neighbor, path);
                }
            }
        }

        if (!path.isEmpty()) {
            path.remove(path.size() - 1);
        }
        visited[currentNode] = false;
    }

    public void printAllCycles() {
        for (List<Integer> cycle : cycles) {
            for (int i = 0; i < cycle.size(); i++) {
                System.out.print(cycle.get(i) + " -> ");
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