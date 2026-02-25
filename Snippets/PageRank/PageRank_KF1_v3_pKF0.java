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
            int numberOfPages = readNumberOfPages(scanner);
            PageRankCalculator calculator = new PageRankCalculator();
            calculator.readAdjacencyMatrix(scanner, numberOfPages);
            calculator.calculatePageRank(numberOfPages);
        }
    }

    private static int readNumberOfPages(Scanner scanner) {
        System.out.print("Enter the Number of WebPages: ");
        return scanner.nextInt();
    }

    private void readAdjacencyMatrix(Scanner scanner, int numberOfPages) {
        System.out.println(
            "Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: "
        );
        for (int from = 1; from <= numberOfPages; from++) {
            for (int to = 1; to <= numberOfPages; to++) {
                int value = scanner.nextInt();
                adjacencyMatrix[from][to] = (from == to) ? 0 : value;
            }
        }
    }

    public void calculatePageRank(int numberOfPages) {
        double initialRank = 1.0 / numberOfPages;
        double[] previousRanks = new double[MAX_PAGES];

        System.out.printf(
            "Total Number of Nodes: %d\tInitial PageRank of All Nodes: %.6f%n",
            numberOfPages,
            initialRank
        );

        initializePageRanks(numberOfPages, initialRank);

        System.out.println("\nInitial PageRank Values, 0th Step");
        printPageRanks(numberOfPages);

        for (int step = 1; step <= ITERATIONS; step++) {
            resetAndCopyRanks(numberOfPages, previousRanks);
            distributeRank(numberOfPages, previousRanks);

            System.out.printf("%nAfter %dth Step%n", step);
            printPageRanks(numberOfPages);
        }

        applyDampingFactor(numberOfPages);

        System.out.println("\nFinal Page Rank:");
        printPageRanks(numberOfPages);
    }

    private void initializePageRanks(int numberOfPages, double initialRank) {
        for (int page = 1; page <= numberOfPages; page++) {
            pageRanks[page] = initialRank;
        }
    }

    private void resetAndCopyRanks(int numberOfPages, double[] previousRanks) {
        for (int page = 1; page <= numberOfPages; page++) {
            previousRanks[page] = pageRanks[page];
            pageRanks[page] = 0.0;
        }
    }

    private void distributeRank(int numberOfPages, double[] previousRanks) {
        for (int to = 1; to <= numberOfPages; to++) {
            for (int from = 1; from <= numberOfPages; from++) {
                if (adjacencyMatrix[from][to] == 1) {
                    double outDegree = calculateOutDegree(numberOfPages, from);
                    if (outDegree > 0) {
                        pageRanks[to] += previousRanks[from] / outDegree;
                    }
                }
            }
        }
    }

    private double calculateOutDegree(int numberOfPages, int from) {
        double outDegree = 0.0;
        for (int to = 1; to <= numberOfPages; to++) {
            if (adjacencyMatrix[from][to] == 1) {
                outDegree++;
            }
        }
        return outDegree;
    }

    private void applyDampingFactor(int numberOfPages) {
        for (int page = 1; page <= numberOfPages; page++) {
            pageRanks[page] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRanks[page];
        }
    }

    private void printPageRanks(int numberOfPages) {
        for (int page = 1; page <= numberOfPages; page++) {
            System.out.printf("Page Rank of %d is:\t%.6f%n", page, pageRanks[page]);
        }
    }
}