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
            int totalNodes = readNumberOfPages(scanner);
            PageRank pageRankCalculator = new PageRank();
            pageRankCalculator.readAdjacencyMatrix(scanner, totalNodes);
            pageRankCalculator.calculate(totalNodes);
        }
    }

    private static int readNumberOfPages(Scanner scanner) {
        System.out.print("Enter the Number of WebPages: ");
        return scanner.nextInt();
    }

    private void readAdjacencyMatrix(Scanner scanner, int totalNodes) {
        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int from = 1; from <= totalNodes; from++) {
            for (int to = 1; to <= totalNodes; to++) {
                adjacencyMatrix[from][to] = scanner.nextInt();
                if (from == to) {
                    adjacencyMatrix[from][to] = 0;
                }
            }
        }
    }

    public void calculate(int totalNodes) {
        double initialPageRank = 1.0 / totalNodes;
        double[] previousPageRank = new double[MAX_NODES];

        System.out.printf(
            "Total Number of Nodes: %d\t Initial PageRank of All Nodes: %.6f%n",
            totalNodes,
            initialPageRank
        );

        initializePageRanks(totalNodes, initialPageRank);

        System.out.println("\nInitial PageRank Values, 0th Step");
        printPageRanks(totalNodes);

        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            copyCurrentToPrevious(totalNodes, previousPageRank);
            resetCurrentPageRanks(totalNodes);

            distributePageRank(totalNodes, previousPageRank);
            applyDampingFactor(totalNodes);

            System.out.printf("%nAfter %dth Step%n", iteration);
            printPageRanks(totalNodes);
        }

        System.out.println("\nFinal Page Rank:");
        printPageRanks(totalNodes);
    }

    private void initializePageRanks(int totalNodes, double initialPageRank) {
        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = initialPageRank;
        }
    }

    private void copyCurrentToPrevious(int totalNodes, double[] previousPageRank) {
        for (int i = 1; i <= totalNodes; i++) {
            previousPageRank[i] = pageRank[i];
        }
    }

    private void resetCurrentPageRanks(int totalNodes) {
        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = 0.0;
        }
    }

    private void distributePageRank(int totalNodes, double[] previousPageRank) {
        for (int targetNode = 1; targetNode <= totalNodes; targetNode++) {
            for (int sourceNode = 1; sourceNode <= totalNodes; sourceNode++) {
                if (adjacencyMatrix[sourceNode][targetNode] == 1) {
                    double outgoingLinks = countOutgoingLinks(sourceNode, totalNodes);
                    if (outgoingLinks > 0) {
                        pageRank[targetNode] += previousPageRank[sourceNode] / outgoingLinks;
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
            System.out.printf("Page Rank of %d is:\t%.6f%n", i, pageRank[i]);
        }
    }
}