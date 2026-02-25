package com.thealgorithms.others;

import java.util.Scanner;

class PageRankCalculator {

    private static final int MAX_PAGES = 10;
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATIONS = 2;

    private final int[][] adjacencyMatrix = new int[MAX_PAGES][MAX_PAGES];
    private final double[] pageRanks = new double[MAX_PAGES];

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the Number of WebPages: ");
            int numberOfPages = scanner.nextInt();

            PageRankCalculator calculator = new PageRankCalculator();
            calculator.readAdjacencyMatrix(scanner, numberOfPages);
            calculator.computePageRank(numberOfPages);
        }
    }

    private void readAdjacencyMatrix(Scanner scanner, int numberOfPages) {
        System.out.println(
            "Enter the Adjacency Matrix (1 = link exists, 0 = no link) between web pages:"
        );
        for (int i = 1; i <= numberOfPages; i++) {
            for (int j = 1; j <= numberOfPages; j++) {
                adjacencyMatrix[i][j] = scanner.nextInt();
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;
                }
            }
        }
    }

    public void computePageRank(int numberOfPages) {
        double initialRank = 1.0 / numberOfPages;
        double[] previousPageRanks = new double[MAX_PAGES];

        System.out.printf(
            "Total Number of Nodes: %d\t Initial PageRank of All Nodes: %.6f%n",
            numberOfPages,
            initialRank
        );

        initializePageRanks(numberOfPages, initialRank);
        printPageRanks(numberOfPages, "Initial PageRank Values, 0th Step");

        for (int step = 1; step <= ITERATIONS; step++) {
            copyArray(pageRanks, previousPageRanks, numberOfPages);
            resetArray(pageRanks, numberOfPages);

            updatePageRanks(numberOfPages, previousPageRanks);

            printPageRanks(numberOfPages, String.format("After %dth Step", step));
        }

        applyDampingFactor(numberOfPages);
        printPageRanks(numberOfPages, "Final Page Rank:");
    }

    private void initializePageRanks(int numberOfPages, double initialRank) {
        for (int i = 1; i <= numberOfPages; i++) {
            pageRanks[i] = initialRank;
        }
    }

    private void copyArray(double[] source, double[] destination, int numberOfPages) {
        for (int i = 1; i <= numberOfPages; i++) {
            destination[i] = source[i];
        }
    }

    private void resetArray(double[] array, int numberOfPages) {
        for (int i = 1; i <= numberOfPages; i++) {
            array[i] = 0.0;
        }
    }

    private void updatePageRanks(int numberOfPages, double[] previousPageRanks) {
        for (int target = 1; target <= numberOfPages; target++) {
            for (int source = 1; source <= numberOfPages; source++) {
                if (adjacencyMatrix[source][target] == 1) {
                    int outDegree = calculateOutDegree(source, numberOfPages);
                    if (outDegree > 0) {
                        pageRanks[target] += previousPageRanks[source] * (1.0 / outDegree);
                    }
                }
            }
        }
    }

    private int calculateOutDegree(int page, int numberOfPages) {
        int outDegree = 0;
        for (int k = 1; k <= numberOfPages; k++) {
            if (adjacencyMatrix[page][k] == 1) {
                outDegree++;
            }
        }
        return outDegree;
    }

    private void applyDampingFactor(int numberOfPages) {
        for (int i = 1; i <= numberOfPages; i++) {
            pageRanks[i] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRanks[i];
        }
    }

    private void printPageRanks(int numberOfPages, String header) {
        System.out.println("\n" + header);
        for (int i = 1; i <= numberOfPages; i++) {
            System.out.printf("Page Rank of %d is:\t%.6f%n", i, pageRanks[i]);
        }
    }
}