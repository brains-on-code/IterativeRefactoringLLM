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

        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int from = 1; from <= numberOfPages; from++) {
            for (int to = 1; to <= numberOfPages; to++) {
                calculator.adjacencyMatrix[from][to] = scanner.nextInt();
                if (from == to) {
                    calculator.adjacencyMatrix[from][to] = 0;
                }
            }
        }

        calculator.calculatePageRank(numberOfPages);
        scanner.close();
    }

    public void calculatePageRank(int numberOfPages) {
        double initialRank = 1.0 / numberOfPages;
        double[] previousRanks = new double[MAX_PAGES];

        System.out.printf("Total Number of Nodes: %d\tInitial PageRank of All Nodes: %.6f%n",
                numberOfPages, initialRank);

        for (int i = 1; i <= numberOfPages; i++) {
            pageRanks[i] = initialRank;
        }

        System.out.println("\nInitial PageRank Values, 0th Step");
        printPageRanks(numberOfPages);

        for (int step = 1; step <= ITERATIONS; step++) {
            for (int i = 1; i <= numberOfPages; i++) {
                previousRanks[i] = pageRanks[i];
                pageRanks[i] = 0.0;
            }

            for (int to = 1; to <= numberOfPages; to++) {
                for (int from = 1; from <= numberOfPages; from++) {
                    if (adjacencyMatrix[from][to] == 1) {
                        double outDegree = 0.0;
                        for (int k = 1; k <= numberOfPages; k++) {
                            if (adjacencyMatrix[from][k] == 1) {
                                outDegree++;
                            }
                        }
                        if (outDegree > 0) {
                            pageRanks[to] += previousRanks[from] * (1.0 / outDegree);
                        }
                    }
                }
            }

            System.out.printf("%nAfter %dth Step%n", step);
            printPageRanks(numberOfPages);
        }

        for (int i = 1; i <= numberOfPages; i++) {
            pageRanks[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRanks[i];
        }

        System.out.println("\nFinal Page Rank:");
        printPageRanks(numberOfPages);
    }

    private void printPageRanks(int numberOfPages) {
        for (int i = 1; i <= numberOfPages; i++) {
            System.out.printf("Page Rank of %d is:\t%.6f%n", i, pageRanks[i]);
        }
    }
}