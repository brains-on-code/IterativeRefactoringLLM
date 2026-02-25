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
        for (int rowIndex = 1; rowIndex <= numberOfPages; rowIndex++) {
            for (int columnIndex = 1; columnIndex <= numberOfPages; columnIndex++) {
                pageRankCalculator.adjacencyMatrix[rowIndex][columnIndex] = scanner.nextInt();
                if (columnIndex == rowIndex) {
                    pageRankCalculator.adjacencyMatrix[rowIndex][columnIndex] = 0;
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

        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            this.pageRanks[pageIndex] = initialPageRank;
        }

        System.out.print("\n Initial PageRank Values , 0th Step \n");
        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
        }

        int currentIteration = 1;
        while (currentIteration <= ITERATION_LIMIT) {
            for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
                previousPageRanks[pageIndex] = this.pageRanks[pageIndex];
                this.pageRanks[pageIndex] = 0;
            }

            for (int targetPageIndex = 1; targetPageIndex <= totalPages; targetPageIndex++) {
                for (int sourcePageIndex = 1; sourcePageIndex <= totalPages; sourcePageIndex++) {
                    if (this.adjacencyMatrix[sourcePageIndex][targetPageIndex] == 1) {
                        double outgoingLinksCount = 0;

                        for (int possibleTargetIndex = 1; possibleTargetIndex <= totalPages; possibleTargetIndex++) {
                            if (this.adjacencyMatrix[sourcePageIndex][possibleTargetIndex] == 1) {
                                outgoingLinksCount++;
                            }
                        }

                        if (outgoingLinksCount > 0) {
                            this.pageRanks[targetPageIndex] +=
                                previousPageRanks[sourcePageIndex] * (1.0 / outgoingLinksCount);
                        }
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", currentIteration);
            for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
                System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
            }

            currentIteration++;
        }

        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            this.pageRanks[pageIndex] =
                (1 - DAMPING_FACTOR) + DAMPING_FACTOR * this.pageRanks[pageIndex];
        }

        System.out.print("\n Final Page Rank : \n");
        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
        }
    }
}