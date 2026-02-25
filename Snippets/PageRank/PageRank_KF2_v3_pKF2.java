package com.thealgorithms.others;

import java.util.Scanner;

class PageRank {

    private static final int MAX_NODES = 10;
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATIONS = 2;

    private final int[][] adjacencyMatrix = new int[MAX_NODES][MAX_NODES];
    private final double[] pageRank = new double[MAX_NODES];

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int totalNodes = readNumberOfNodes(scanner);
            if (totalNodes == -1) {
                return;
            }

            PageRank pageRankCalculator = new PageRank();
            pageRankCalculator.readAdjacencyMatrix(scanner, totalNodes);
            pageRankCalculator.calculate(totalNodes);
        }
    }

    private static int readNumberOfNodes(Scanner scanner) {
        System.out.print("Enter the number of webpages (max " + MAX_NODES + "): ");
        int nodes = scanner.nextInt();

        if (nodes <= 0 || nodes >= MAX_NODES) {
            System.out.println("Number of webpages must be between 1 and " + (MAX_NODES - 1));
            return -1;
        }
        return nodes;
    }

    private void readAdjacencyMatrix(Scanner scanner, int totalNodes) {
        System.out.println("Enter the adjacency matrix (1 = link exists, 0 = no link):");
        for (int from = 1; from <= totalNodes; from++) {
            for (int to = 1; to <= totalNodes; to++) {
                int value = scanner.nextInt();
                adjacencyMatrix[from][to] = (from == to) ? 0 : value;
            }
        }
    }

    public void calculate(int totalNodes) {
        double initialPageRank = 1.0 / totalNodes;
        double[] previousPageRank = new double[MAX_NODES];

        System.out.printf(
            "Total number of nodes: %d\tInitial PageRank of all nodes: %.6f%n",
            totalNodes,
            initialPageRank
        );

        initializePageRanks(totalNodes, initialPageRank);

        System.out.println("\nInitial PageRank values (step 0):");
        printPageRanks(totalNodes);

        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            copyCurrentToPreviousPageRanks(totalNodes, previousPageRank);
            resetCurrentPageRanks(totalNodes);

            updatePageRanksFromLinks(totalNodes, previousPageRank);

            System.out.printf("%nAfter step %d:%n", iteration);
            printPageRanks(totalNodes);
        }

        applyDampingFactor(totalNodes);

        System.out.println("\nFinal PageRank:");
        printPageRanks(totalNodes);
    }

    private void initializePageRanks(int totalNodes, double initialPageRank) {
        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = initialPageRank;
        }
    }

    private void copyCurrentToPreviousPageRanks(int totalNodes, double[] previousPageRank) {
        for (int i = 1; i <= totalNodes; i++) {
            previousPageRank[i] = pageRank[i];
        }
    }

    private void resetCurrentPageRanks(int totalNodes) {
        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = 0;
        }
    }

    private void updatePageRanksFromLinks(int totalNodes, double[] previousPageRank) {
        for (int targetNode = 1; targetNode <= totalNodes; targetNode++) {
            for (int sourceNode = 1; sourceNode <= totalNodes; sourceNode++) {
                if (adjacencyMatrix[sourceNode][targetNode] == 1) {
                    double outgoingLinks = countOutgoingLinks(sourceNode, totalNodes);
                    if (outgoingLinks > 0) {
                        pageRank[targetNode] += previousPageRank[sourceNode] * (1.0 / outgoingLinks);
                    }
                }
            }
        }
    }

    private void applyDampingFactor(int totalNodes) {
        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRank[i];
        }
    }

    private double countOutgoingLinks(int node, int totalNodes) {
        double outgoingLinks = 0;
        for (int i = 1; i <= totalNodes; i++) {
            if (adjacencyMatrix[node][i] == 1) {
                outgoingLinks++;
            }
        }
        return outgoingLinks;
    }

    private void printPageRanks(int totalNodes) {
        for (int i = 1; i <= totalNodes; i++) {
            System.out.printf("PageRank of node %d:\t%.6f%n", i, pageRank[i]);
        }
    }
}