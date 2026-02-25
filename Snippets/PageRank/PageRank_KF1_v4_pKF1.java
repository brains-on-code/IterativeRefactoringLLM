package com.thealgorithms.others;

import java.util.Scanner;

class PageRankCalculator {

    private static final int MAX_PAGES = 10;
    private static final double DAMPING_FACTOR = 0.85;

    public int[][] adjacencyMatrix = new int[MAX_PAGES][MAX_PAGES];
    public double[] pageRanks = new double[MAX_PAGES];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Number of WebPages: ");
        int numberOfPages = scanner.nextInt();

        PageRankCalculator calculator = new PageRankCalculator();

        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int rowIndex = 1; rowIndex <= numberOfPages; rowIndex++) {
            for (int columnIndex = 1; columnIndex <= numberOfPages; columnIndex++) {
                calculator.adjacencyMatrix[rowIndex][columnIndex] = scanner.nextInt();
                if (columnIndex == rowIndex) {
                    calculator.adjacencyMatrix[rowIndex][columnIndex] = 0;
                }
            }
        }

        calculator.calculatePageRank(numberOfPages);
    }

    public void calculatePageRank(double numberOfPages) {
        double initialRank = 1 / numberOfPages;
        double outgoingLinkCount;
        double[] previousPageRanks = new double[MAX_PAGES];
        int iterationCount = 1;

        System.out.printf(
            " Total Number of Nodes :%s\t Initial PageRank of All Nodes :%s%n",
            numberOfPages,
            initialRank
        );

        for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
            this.pageRanks[pageIndex] = initialRank;
        }

        System.out.print("\n Initial PageRank Values , 0th Step \n");
        for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
        }

        while (iterationCount <= 2) {
            for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
                previousPageRanks[pageIndex] = this.pageRanks[pageIndex];
                this.pageRanks[pageIndex] = 0;
            }

            for (int targetPageIndex = 1; targetPageIndex <= numberOfPages; targetPageIndex++) {
                for (int sourcePageIndex = 1; sourcePageIndex <= numberOfPages; sourcePageIndex++) {
                    if (this.adjacencyMatrix[sourcePageIndex][targetPageIndex] == 1) {
                        outgoingLinkCount = 0;
                        for (int neighborPageIndex = 1; neighborPageIndex <= numberOfPages; neighborPageIndex++) {
                            if (this.adjacencyMatrix[sourcePageIndex][neighborPageIndex] == 1) {
                                outgoingLinkCount++;
                            }
                        }
                        this.pageRanks[targetPageIndex] += previousPageRanks[sourcePageIndex] * (1 / outgoingLinkCount);
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iterationCount);
            for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++) {
                System.out.printf(" Page Rank of %d is :\t%f%n", pageIndex, this.pageRanks[pageIndex]);
            }

            iterationCount++;
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