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
        System.out.print("Enter the number of nodes: ");
        nodeCount = scanner.nextInt();
        System.out.print("Enter the number of edges: ");
        final int edgeCount = scanner.nextInt();

        adjacencyMatrix = new int[nodeCount][nodeCount];
        visited = new boolean[nodeCount];

        System.out.println("Enter the details of each edge <Start Node> <End Node>");

        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            int source = scanner.nextInt();
            int target = scanner.nextInt();
            adjacencyMatrix[source][target] = 1;
        }
        scanner.close();
    }

    public void findCycles() {
        for (int startNode = 0; startNode < nodeCount; startNode++) {
            List<Integer> path = new ArrayList<>();
            depthFirstSearch(startNode, startNode, path);
            removeIncidentEdges(startNode);
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

    private void removeIncidentEdges(int node) {
        for (int neighbor = 0; neighbor < nodeCount; neighbor++) {
            adjacencyMatrix[node][neighbor] = 0;
            adjacencyMatrix[neighbor][node] = 0;
        }
    }

    public void printCycles() {
        for (List<Integer> cycle : cycles) {
            for (int index = 0; index < cycle.size(); index++) {
                System.out.print(cycle.get(index) + " -> ");
            }
            System.out.println(cycle.get(0));
            System.out.println();
        }
    }
}

public final class GraphCycleDemo {
    private GraphCycleDemo() {}

    public static void main(String[] args) {
        GraphCycleFinder graphCycleFinder = new GraphCycleFinder();
        graphCycleFinder.findCycles();
        graphCycleFinder.printCycles();
    }
}