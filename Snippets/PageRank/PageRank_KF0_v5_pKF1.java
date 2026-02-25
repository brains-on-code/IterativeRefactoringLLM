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

    public void calculatePageRank(int numberOfPages) {
        double initialPageRank = 1.0 / numberOfPages;
        double[] previousPageRanks = new double[MAX_PAGES];

        System.out.printf(
            " Total Number of Nodes : %d\t Initial PageRank of All Nodes : %f%n",
            numberOfPages,
            initialPageRank
        );

        for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
            this.pageRanks[pageIndex] = initialPageRank;
        }

        System.out.print("\n Initial PageRank Values , 0th Step \n");
        for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
        }

        int iteration = 1;
        while (iteration <= ITERATION_LIMIT) {
            for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
                previousPageRanks[pageIndex] = this.pageRanks[pageIndex];
                this.pageRanks[pageIndex] = 0;
            }

            for (int targetPageIndex = 1; targetPageIndex <= numberOfPages; targetPageIndex++) {
                for (int sourcePageIndex = 1; sourcePageIndex <= numberOfPages; sourcePageIndex++) {
                    if (this.adjacencyMatrix[sourcePageIndex][targetPageIndex] == 1) {
                        double outgoingLinkCount = 0;

                        for (int outgoingPageIndex = 1; outgoingPageIndex <= numberOfPages; outgoingPageIndex++) {
                            if (this.adjacencyMatrix[sourcePageIndex][outgoingPageIndex] == 1) {
                                outgoingLinkCount++;
                            }
                        }

                        if (outgoingLinkCount > 0) {
                            this.pageRanks[targetPageIndex] +=
                                previousPageRanks[sourcePageIndex] * (1.0 / outgoingLinkCount);
                        }
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iteration);
            for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
                System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
            }

            iteration++;
        }

        for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
            this.pageRanks[pageIndex] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * this.pageRanks[pageIndex];
        }

        System.out.print("\n Final Page Rank : \n");
        for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
        }
    }
}