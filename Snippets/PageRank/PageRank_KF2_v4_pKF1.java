package com.thealgorithms.others;

import java.util.Scanner;

class PageRank {

    private static final int MAX_PAGES = 10;
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATION_LIMIT = 2;

    public int[][] adjacencyMatrix = new int[MAX_PAGES][MAX_PAGES];
    public double[] pageRanks = new double[MAX_PAGES];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Number of WebPages: ");
        int numberOfPages = scanner.nextInt();

        PageRank pageRank = new PageRank();

        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int fromPage = 1; fromPage <= numberOfPages; fromPage++) {
            for (int toPage = 1; toPage <= numberOfPages; toPage++) {
                pageRank.adjacencyMatrix[fromPage][toPage] = scanner.nextInt();
                if (toPage == fromPage) {
                    pageRank.adjacencyMatrix[fromPage][toPage] = 0;
                }
            }
        }

        pageRank.calculatePageRank(numberOfPages);
    }

    public void calculatePageRank(int totalPages) {
        double initialRank = 1.0 / totalPages;
        double[] previousPageRanks = new double[MAX_PAGES];

        System.out.printf(
            " Total Number of Nodes : %d\t Initial PageRank of All Nodes : %.6f%n",
            totalPages,
            initialRank
        );

        for (int page = 1; page <= totalPages; page++) {
            pageRanks[page] = initialRank;
        }

        System.out.println("\n Initial PageRank Values , 0th Step ");
        for (int page = 1; page <= totalPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%.6f%n", page, pageRanks[page]);
        }

        int iteration = 1;
        while (iteration <= ITERATION_LIMIT) {
            for (int page = 1; page <= totalPages; page++) {
                previousPageRanks[page] = pageRanks[page];
                pageRanks[page] = 0;
            }

            for (int targetPage = 1; targetPage <= totalPages; targetPage++) {
                for (int sourcePage = 1; sourcePage <= totalPages; sourcePage++) {
                    if (adjacencyMatrix[sourcePage][targetPage] == 1) {
                        double outgoingLinkCount = 0;
                        for (int possibleTargetPage = 1; possibleTargetPage <= totalPages; possibleTargetPage++) {
                            if (adjacencyMatrix[sourcePage][possibleTargetPage] == 1) {
                                outgoingLinkCount++;
                            }
                        }
                        if (outgoingLinkCount > 0) {
                            pageRanks[targetPage] += previousPageRanks[sourcePage] * (1.0 / outgoingLinkCount);
                        }
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iteration);
            for (int page = 1; page <= totalPages; page++) {
                System.out.printf(" Page Rank of %d is :\t%.6f%n", page, pageRanks[page]);
            }

            iteration++;
        }

        for (int page = 1; page <= totalPages; page++) {
            pageRanks[page] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRanks[page];
        }

        System.out.println("\n Final Page Rank : ");
        for (int page = 1; page <= totalPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%.6f%n", page, pageRanks[page]);
        }
    }
}