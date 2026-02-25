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
        for (int fromPageIndex = 1; fromPageIndex <= numberOfPages; fromPageIndex++) {
            for (int toPageIndex = 1; toPageIndex <= numberOfPages; toPageIndex++) {
                pageRank.adjacencyMatrix[fromPageIndex][toPageIndex] = scanner.nextInt();
                if (toPageIndex == fromPageIndex) {
                    pageRank.adjacencyMatrix[fromPageIndex][toPageIndex] = 0;
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

        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            pageRanks[pageIndex] = initialRank;
        }

        System.out.println("\n Initial PageRank Values , 0th Step ");
        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%.6f%n", pageIndex, pageRanks[pageIndex]);
        }

        int iterationCount = 1;
        while (iterationCount <= ITERATION_LIMIT) {
            for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
                previousPageRanks[pageIndex] = pageRanks[pageIndex];
                pageRanks[pageIndex] = 0;
            }

            for (int targetPageIndex = 1; targetPageIndex <= totalPages; targetPageIndex++) {
                for (int sourcePageIndex = 1; sourcePageIndex <= totalPages; sourcePageIndex++) {
                    if (adjacencyMatrix[sourcePageIndex][targetPageIndex] == 1) {
                        double outgoingLinkCount = 0;
                        for (int possibleTargetIndex = 1; possibleTargetIndex <= totalPages; possibleTargetIndex++) {
                            if (adjacencyMatrix[sourcePageIndex][possibleTargetIndex] == 1) {
                                outgoingLinkCount++;
                            }
                        }
                        if (outgoingLinkCount > 0) {
                            pageRanks[targetPageIndex] += previousPageRanks[sourcePageIndex] * (1.0 / outgoingLinkCount);
                        }
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iterationCount);
            for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
                System.out.printf(" Page Rank of %d is :\t%.6f%n", pageIndex, pageRanks[pageIndex]);
            }

            iterationCount++;
        }

        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            pageRanks[pageIndex] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * pageRanks[pageIndex];
        }

        System.out.println("\n Final Page Rank : ");
        for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%.6f%n", pageIndex, pageRanks[pageIndex]);
        }
    }
}