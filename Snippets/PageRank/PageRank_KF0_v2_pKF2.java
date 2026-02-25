package com.thealgorithms.others;

import java.util.Scanner;

class PageRank {

    private static final int MAX_NODES = 10;
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATIONS = 2;

    private final int[][] adjacencyMatrix = new int[MAX_NODES][MAX_NODES];
    private final double[] pageRank = new double[MAX_NODES];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Number of WebPages: ");
        int nodes = scanner.nextInt();

        PageRank pageRankCalculator = new PageRank();

        System.out.println(
            "Enter the Adjacency Matrix (1 = link exists, 0 = no link) between web pages:"
        );
        for (int from = 1; from <= nodes; from++) {
            for (int to = 1; to <= nodes; to++) {
                pageRankCalculator.adjacencyMatrix[from][to] = scanner.nextInt();
                if (to == from) {
                    pageRankCalculator.adjacencyMatrix[from][to] = 0;
                }
            }
        }

        pageRankCalculator.calculate(nodes);
    }

    public void calculate(int totalNodes) {
        double initialPageRank = 1.0 / totalNodes;
        double[] previousPageRank = new double[MAX_NODES];

        System.out.printf(
            "Total Number of Nodes: %d\tInitial PageRank of All Nodes: %.6f%n",
            totalNodes,
            initialPageRank
        );

        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = initialPageRank;
        }

        System.out.println("\nInitial PageRank Values (0th Step)");
        printPageRanks(totalNodes);

        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            for (int i = 1; i <= totalNodes; i++) {
                previousPageRank[i] = pageRank[i];
                pageRank[i] = 0.0;
            }

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

            System.out.printf("%nAfter %dth Step%n", iteration);
            printPageRanks(totalNodes);
        }

        for (int i = 1; i <= totalNodes; i++) {
            pageRank[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRank[i];
        }

        System.out.println("\nFinal Page Rank:");
        printPageRanks(totalNodes);
    }

    private double countOutgoingLinks(int node, int totalNodes) {
        double outgoingLinks = 0.0;
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