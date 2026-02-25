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
        for (int row = 1; row <= numberOfPages; row++) {
            for (int col = 1; col <= numberOfPages; col++) {
                adjacencyMatrix[row][col] = scanner.nextInt();
                if (row == col) {
                    adjacencyMatrix[row][col] = 0;
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
        for (int page = 1; page <= numberOfPages; page++) {
            pageRanks[page] = initialRank;
        }
    }

    private void copyArray(double[] source, double[] destination, int numberOfPages) {
        for (int page = 1; page <= numberOfPages; page++) {
            destination[page] = source[page];
        }
    }

    private void resetArray(double[] array, int numberOfPages) {
        for (int page = 1; page <= numberOfPages; page++) {
            array[page] = 0.0;
        }
    }

    private void updatePageRanks(int numberOfPages, double[] previousPageRanks) {
        for (int targetPage = 1; targetPage <= numberOfPages; targetPage++) {
            for (int sourcePage = 1; sourcePage <= numberOfPages; sourcePage++) {
                if (adjacencyMatrix[sourcePage][targetPage] == 1) {
                    int outDegree = calculateOutDegree(sourcePage, numberOfPages);
                    if (outDegree > 0) {
                        pageRanks[targetPage] += previousPageRanks[sourcePage] * (1.0 / outDegree);
                    }
                }
            }
        }
    }

    private int calculateOutDegree(int page, int numberOfPages) {
        int outDegree = 0;
        for (int targetPage = 1; targetPage <= numberOfPages; targetPage++) {
            if (adjacencyMatrix[page][targetPage] == 1) {
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

    private void printPageRanks(int numberOfPages, String header) {
        System.out.println("\n" + header);
        for (int page = 1; page <= numberOfPages; page++) {
            System.out.printf("Page Rank of %d is:\t%.6f%n", page, pageRanks[page]);
        }
    }
}