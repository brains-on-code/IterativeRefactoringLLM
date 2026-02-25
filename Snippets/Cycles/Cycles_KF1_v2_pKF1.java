package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GraphCycleFinder {

    private final int numberOfNodes;
    private final int[][] adjacencyMatrix;
    private final boolean[] visitedNodes;
    private final List<List<Integer>> cycles = new ArrayList<>();

    GraphCycleFinder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of nodes: ");
        numberOfNodes = scanner.nextInt();
        System.out.print("Enter the number of edges: ");
        final int numberOfEdges = scanner.nextInt();

        adjacencyMatrix = new int[numberOfNodes][numberOfNodes];
        visitedNodes = new boolean[numberOfNodes];

        System.out.println("Enter the details of each edge <Start Node> <End Node>");

        for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
            int startNode = scanner.nextInt();
            int endNode = scanner.nextInt();
            adjacencyMatrix[startNode][endNode] = 1;
        }
        scanner.close();
    }

    public void findCycles() {
        for (int startNode = 0; startNode < numberOfNodes; startNode++) {
            List<Integer> currentPath = new ArrayList<>();
            depthFirstSearch(startNode, startNode, currentPath);
            removeOutgoingAndIncomingEdges(startNode);
        }
    }

    private void depthFirstSearch(int startNode, int currentNode, List<Integer> currentPath) {
        currentPath.add(currentNode);
        visitedNodes[currentNode] = true;

        for (int neighborNode = 0; neighborNode < numberOfNodes; neighborNode++) {
            if (adjacencyMatrix[currentNode][neighborNode] == 1) {
                if (neighborNode == startNode) {
                    cycles.add(new ArrayList<>(currentPath));
                } else if (!visitedNodes[neighborNode]) {
                    depthFirstSearch(startNode, neighborNode, currentPath);
                }
            }
        }

        if (!currentPath.isEmpty()) {
            currentPath.remove(currentPath.size() - 1);
        }
        visitedNodes[currentNode] = false;
    }

    private void removeOutgoingAndIncomingEdges(int node) {
        for (int neighborNode = 0; neighborNode < numberOfNodes; neighborNode++) {
            adjacencyMatrix[node][neighborNode] = 0;
            adjacencyMatrix[neighborNode][node] = 0;
        }
    }

    public void printCycles() {
        for (List<Integer> cycle : cycles) {
            for (int nodeIndex = 0; nodeIndex < cycle.size(); nodeIndex++) {
                System.out.print(cycle.get(nodeIndex) + " -> ");
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