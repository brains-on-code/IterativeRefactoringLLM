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

        PageRank pageRankCalculator = new PageRank();

        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int row = 1; row <= numberOfPages; row++) {
            for (int col = 1; col <= numberOfPages; col++) {
                pageRankCalculator.adjacencyMatrix[row][col] = scanner.nextInt();
                if (col == row) {
                    pageRankCalculator.adjacencyMatrix[row][col] = 0;
                }
            }
        }

        pageRankCalculator.calculatePageRank(numberOfPages);
    }

    public void calculatePageRank(int totalPages) {
        double initialPageRank = 1.0 / totalPages;
        double[] previousPageRanks = new double[MAX_PAGES];

        System.out.printf(
            " Total Number of Nodes : %d\t Initial PageRank of All Nodes : %f%n",
            totalPages,
            initialPageRank
        );

        for (int page = 1; page <= totalPages; page++) {
            this.pageRanks[page] = initialPageRank;
        }

        System.out.print("\n Initial PageRank Values , 0th Step \n");
        for (int page = 1; page <= totalPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRanks[page]);
        }

        int currentIteration = 1;
        while (currentIteration <= ITERATION_LIMIT) {
            for (int page = 1; page <= totalPages; page++) {
                previousPageRanks[page] = this.pageRanks[page];
                this.pageRanks[page] = 0;
            }

            for (int targetPage = 1; targetPage <= totalPages; targetPage++) {
                for (int sourcePage = 1; sourcePage <= totalPages; sourcePage++) {
                    if (this.adjacencyMatrix[sourcePage][targetPage] == 1) {
                        double outgoingLinksCount = 0;

                        for (int possibleLink = 1; possibleLink <= totalPages; possibleLink++) {
                            if (this.adjacencyMatrix[sourcePage][possibleLink] == 1) {
                                outgoingLinksCount++;
                            }
                        }

                        if (outgoingLinksCount > 0) {
                            this.pageRanks[targetPage] +=
                                previousPageRanks[sourcePage] * (1.0 / outgoingLinksCount);
                        }
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", currentIteration);
            for (int page = 1; page <= totalPages; page++) {
                System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRanks[page]);
            }

            currentIteration++;
        }

        for (int page = 1; page <= totalPages; page++) {
            this.pageRanks[page] =
                (1 - DAMPING_FACTOR) + DAMPING_FACTOR * this.pageRanks[page];
        }

        System.out.print("\n Final Page Rank : \n");
        for (int page = 1; page <= totalPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRanks[page]);
        }
    }
}