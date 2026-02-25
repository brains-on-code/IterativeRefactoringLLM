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
            System.out.print("Enter the number of webpages (max " + MAX_NODES + "): ");
            int nodes = scanner.nextInt();

            if (nodes <= 0 || nodes >= MAX_NODES) {
                System.out.println("Number of webpages must be between 1 and " + (MAX_NODES - 1));
                return;
            }

            PageRank pageRankCalculator = new PageRank();

            System.out.println("Enter the adjacency matrix (1 = link exists, 0 = no link):");
            for (int from = 1; from <= nodes; from++) {
                for (int to = 1; to <= nodes; to++) {
                    pageRankCalculator.adjacencyMatrix[from][to] = scanner.nextInt();
                    if (from == to) {
                        pageRankCalculator.adjacencyMatrix[from][to] = 0;
                    }
                }
            }

            pageRankCalculator.calculate(nodes);
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

        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = initialPageRank;
        }

        System.out.println("\nInitial PageRank values (step 0):");
        printPageRanks(totalNodes);

        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            for (int i = 1; i <= totalNodes; i++) {
                previousPageRank[i] = pageRank[i];
                pageRank[i] = 0;
            }

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

            System.out.printf("%nAfter step %d:%n", iteration);
            printPageRanks(totalNodes);
        }

        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRank[i];
        }

        System.out.println("\nFinal PageRank:");
        printPageRanks(totalNodes);
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