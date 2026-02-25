package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cycle {

    private final int nodes;
    private final int[][] adjacencyMatrix;
    private final boolean[] visited;
    private final List<List<Integer>> cycles = new ArrayList<>();

    Cycle() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of nodes: ");
            nodes = scanner.nextInt();

            System.out.print("Enter the number of edges: ");
            int edges = scanner.nextInt();

            adjacencyMatrix = new int[nodes][nodes];
            visited = new boolean[nodes];

            System.out.println("Enter the details of each edge: <Start Node> <End Node>");
            for (int i = 0; i < edges; i++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                adjacencyMatrix[start][end] = 1;
            }
        }
    }

    public void start() {
        for (int i = 0; i < nodes; i++) {
            List<Integer> path = new ArrayList<>();
            dfs(i, i, path);
            clearNodeEdges(i);
        }
    }

    private void dfs(int start, int current, List<Integer> path) {
        path.add(current);
        visited[current] = true;

        for (int neighbor = 0; neighbor < nodes; neighbor++) {
            if (adjacencyMatrix[current][neighbor] == 1) {
                if (neighbor == start) {
                    cycles.add(new ArrayList<>(path));
                } else if (!visited[neighbor]) {
                    dfs(start, neighbor, path);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[current] = false;
    }

    private void clearNodeEdges(int node) {
        for (int i = 0; i < nodes; i++) {
            adjacencyMatrix[node][i] = 0;
            adjacencyMatrix[i][node] = 0;
        }
    }

    public void printAll() {
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
        cycleFinder.start();
        cycleFinder.printAll();
    }
}