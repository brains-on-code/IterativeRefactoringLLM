package com.thealgorithms.others;

import java.util.Scanner;

class PageRank {

    private static final int MAX_PAGES = 10;
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATION_LIMIT = 2;

    public int[][] linkMatrix = new int[MAX_PAGES][MAX_PAGES];
    public double[] ranks = new double[MAX_PAGES];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Number of WebPages: ");
        int pageCount = scanner.nextInt();

        PageRank pageRank = new PageRank();

        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int fromPage = 1; fromPage <= pageCount; fromPage++) {
            for (int toPage = 1; toPage <= pageCount; toPage++) {
                pageRank.linkMatrix[fromPage][toPage] = scanner.nextInt();
                if (toPage == fromPage) {
                    pageRank.linkMatrix[fromPage][toPage] = 0;
                }
            }
        }

        pageRank.calculatePageRank(pageCount);
    }

    public void calculatePageRank(int pageCount) {
        double initialRank = 1.0 / pageCount;
        double[] previousRanks = new double[MAX_PAGES];

        System.out.printf(
            " Total Number of Nodes : %d\t Initial PageRank of All Nodes : %.6f%n",
            pageCount,
            initialRank
        );

        for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
            ranks[pageIndex] = initialRank;
        }

        System.out.println("\n Initial PageRank Values , 0th Step ");
        for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%.6f%n", pageIndex, ranks[pageIndex]);
        }

        int iteration = 1;
        while (iteration <= ITERATION_LIMIT) {
            for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
                previousRanks[pageIndex] = ranks[pageIndex];
                ranks[pageIndex] = 0;
            }

            for (int targetPageIndex = 1; targetPageIndex <= pageCount; targetPageIndex++) {
                for (int sourcePageIndex = 1; sourcePageIndex <= pageCount; sourcePageIndex++) {
                    if (linkMatrix[sourcePageIndex][targetPageIndex] == 1) {
                        double outgoingLinkCount = 0;
                        for (int possibleTargetIndex = 1; possibleTargetIndex <= pageCount; possibleTargetIndex++) {
                            if (linkMatrix[sourcePageIndex][possibleTargetIndex] == 1) {
                                outgoingLinkCount++;
                            }
                        }
                        if (outgoingLinkCount > 0) {
                            ranks[targetPageIndex] += previousRanks[sourcePageIndex] * (1.0 / outgoingLinkCount);
                        }
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iteration);
            for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
                System.out.printf(" Page Rank of %d is :\t%.6f%n", pageIndex, ranks[pageIndex]);
            }

            iteration++;
        }

        for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
            ranks[pageIndex] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * ranks[pageIndex];
        }

        System.out.println("\n Final Page Rank : ");
        for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%.6f%n", pageIndex, ranks[pageIndex]);
        }
    }
}