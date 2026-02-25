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
            " Total Number of Nodes : %d\t Initial PageRank of All Nodes : %f%n",
            pageCount,
            initialRank
        );

        for (int page = 1; page <= pageCount; page++) {
            this.ranks[page] = initialRank;
        }

        System.out.print("\n Initial PageRank Values , 0th Step \n");
        for (int page = 1; page <= pageCount; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.ranks[page]);
        }

        int iteration = 1;
        while (iteration <= ITERATION_LIMIT) {
            for (int page = 1; page <= pageCount; page++) {
                previousRanks[page] = this.ranks[page];
                this.ranks[page] = 0;
            }

            for (int targetPage = 1; targetPage <= pageCount; targetPage++) {
                for (int sourcePage = 1; sourcePage <= pageCount; sourcePage++) {
                    if (this.linkMatrix[sourcePage][targetPage] == 1) {
                        double outgoingLinkCount = 0;

                        for (int possibleTarget = 1; possibleTarget <= pageCount; possibleTarget++) {
                            if (this.linkMatrix[sourcePage][possibleTarget] == 1) {
                                outgoingLinkCount++;
                            }
                        }

                        if (outgoingLinkCount > 0) {
                            this.ranks[targetPage] +=
                                previousRanks[sourcePage] * (1.0 / outgoingLinkCount);
                        }
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iteration);
            for (int page = 1; page <= pageCount; page++) {
                System.out.printf(" Page Rank of %d is :\t%f%n", page, this.ranks[page]);
            }

            iteration++;
        }

        for (int page = 1; page <= pageCount; page++) {
            this.ranks[page] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * this.ranks[page];
        }

        System.out.print("\n Final Page Rank : \n");
        for (int page = 1; page <= pageCount; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.ranks[page]);
        }
    }
}