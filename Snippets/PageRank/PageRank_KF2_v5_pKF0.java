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
            int nodeCount = readNodeCount(scanner);
            PageRank pageRankCalculator = new PageRank();
            pageRankCalculator.readAdjacencyMatrix(scanner, nodeCount);
            pageRankCalculator.calculate(nodeCount);
        }
    }

    private static int readNodeCount(Scanner scanner) {
        System.out.print("Enter the Number of WebPages: ");
        return scanner.nextInt();
    }

    private void readAdjacencyMatrix(Scanner scanner, int nodeCount) {
        System.out.println(
                "Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int row = 1; row <= nodeCount; row++) {
            for (int col = 1; col <= nodeCount; col++) {
                int value = scanner.nextInt();
                adjacencyMatrix[row][col] = (row == col) ? 0 : value;
            }
        }
    }

    public void calculate(int totalNodes) {
        double initialPageRank = 1.0 / totalNodes;
        double[] previousPageRank = new double[MAX_NODES];

        System.out.printf(
                "Total Number of Nodes: %d\tInitial PageRank of All Nodes: %.6f%n",
                totalNodes, initialPageRank);

        initializePageRanks(totalNodes, initialPageRank);

        System.out.println("\nInitial PageRank Values, 0th Step");
        printPageRanks(totalNodes);

        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            copyPageRanks(previousPageRank, totalNodes);
            resetPageRanks(totalNodes);
            distributePageRank(previousPageRank, totalNodes);

            System.out.printf("%nAfter %dth Step%n", iteration);
            printPageRanks(totalNodes);
        }

        applyDampingFactor(totalNodes);

        System.out.println("\nFinal Page Rank:");
        printPageRanks(totalNodes);
    }

    private void initializePageRanks(int totalNodes, double initialPageRank) {
        for (int node = 1; node <= totalNodes; node++) {
            pageRank[node] = initialPageRank;
        }
    }

    private void copyPageRanks(double[] destination, int totalNodes) {
        for (int node = 1; node <= totalNodes; node++) {
            destination[node] = pageRank[node];
        }
    }

    private void resetPageRanks(int totalNodes) {
        for (int node = 1; node <= totalNodes; node++) {
            pageRank[node] = 0.0;
        }
    }

    private void distributePageRank(double[] previousPageRank, int totalNodes) {
        for (int targetNode = 1; targetNode <= totalNodes; targetNode++) {
            for (int sourceNode = 1; sourceNode <= totalNodes; sourceNode++) {
                if (hasEdge(sourceNode, targetNode)) {
                    double outgoingLinks = countOutgoingLinks(sourceNode, totalNodes);
                    if (outgoingLinks > 0) {
                        pageRank[targetNode] += previousPageRank[sourceNode] / outgoingLinks;
                    }
                }
            }
        }
    }

    private void applyDampingFactor(int totalNodes) {
        for (int node = 1; node <= totalNodes; node++) {
            pageRank[node] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRank[node];
        }
    }

    private double countOutgoingLinks(int node, int totalNodes) {
        double outgoingLinks = 0.0;
        for (int targetNode = 1; targetNode <= totalNodes; targetNode++) {
            if (hasEdge(node, targetNode)) {
                outgoingLinks++;
            }
        }
        return outgoingLinks;
    }

    private boolean hasEdge(int fromNode, int toNode) {
        return adjacencyMatrix[fromNode][toNode] == 1;
    }

    private void printPageRanks(int totalNodes) {
        for (int node = 1; node <= totalNodes; node++) {
            System.out.printf("Page Rank of %d is:\t%.6f%n", node, pageRank[node]);
        }
    }
}