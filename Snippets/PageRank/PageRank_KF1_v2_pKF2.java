package com.thealgorithms.others;

import java.util.Scanner;

class PageRankCalculator {

    private static final int MAX_PAGES = 10;
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATIONS = 2;

    private final int[][] adjacencyMatrix = new int[MAX_PAGES][MAX_PAGES];
    private final double[] pageRanks = new double[MAX_PAGES];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Number of WebPages: ");
        int numberOfPages = scanner.nextInt();

        PageRankCalculator calculator = new PageRankCalculator();

        System.out.println(
            "Enter the Adjacency Matrix (1 = link exists, 0 = no link) between web pages:"
        );
        for (int i = 1; i <= numberOfPages; i++) {
            for (int j = 1; j <= numberOfPages; j++) {
                calculator.adjacencyMatrix[i][j] = scanner.nextInt();
                // Disallow self-links
                if (i == j) {
                    calculator.adjacencyMatrix[i][j] = 0;
                }
            }
        }

        calculator.computePageRank(numberOfPages);
    }

    public void computePageRank(int numberOfPages) {
        double initialRank = 1.0 / numberOfPages;
        double[] previousPageRanks = new double[MAX_PAGES];

        System.out.printf(
            "Total Number of Nodes: %d\t Initial PageRank of All Nodes: %.6f%n",
            numberOfPages,
            initialRank
        );

        // Initialize all page ranks to the same starting value
        for (int i = 1; i <= numberOfPages; i++) {
            pageRanks[i] = initialRank;
        }

        System.out.println("\nInitial PageRank Values, 0th Step");
        for (int i = 1; i <= numberOfPages; i++) {
            System.out.printf("Page Rank of %d is:\t%.6f%n", i, pageRanks[i]);
        }

        int step = 1;
        while (step <= ITERATIONS) {
            // Preserve current ranks and reset for the next iteration
            for (int i = 1; i <= numberOfPages; i++) {
                previousPageRanks[i] = pageRanks[i];
                pageRanks[i] = 0.0;
            }

            // Distribute rank from each source page to its target pages
            for (int target = 1; target <= numberOfPages; target++) {
                for (int source = 1; source <= numberOfPages; source++) {
                    if (adjacencyMatrix[source][target] == 1) {
                        int outDegree = 0;
                        for (int k = 1; k <= numberOfPages; k++) {
                            if (adjacencyMatrix[source][k] == 1) {
                                outDegree++;
                            }
                        }
                        if (outDegree > 0) {
                            pageRanks[target] += previousPageRanks[source] * (1.0 / outDegree);
                        }
                    }
                }
            }

            System.out.printf("%nAfter %dth Step%n", step);
            for (int i = 1; i <= numberOfPages; i++) {
                System.out.printf("Page Rank of %d is:\t%.6f%n", i, pageRanks[i]);
            }

            step++;
        }

        // Apply damping factor to simulate random jumps
        for (int i = 1; i <= numberOfPages; i++) {
            pageRanks[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRanks[i];
        }

        System.out.println("\nFinal Page Rank:");
        for (int i = 1; i <= numberOfPages; i++) {
            System.out.printf("Page Rank of %d is:\t%.6f%n", i, pageRanks[i]);
        }
    }
}