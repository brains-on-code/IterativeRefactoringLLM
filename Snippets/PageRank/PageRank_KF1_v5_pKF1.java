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
        for (int row = 1; row <= numberOfPages; row++) {
            for (int column = 1; column <= numberOfPages; column++) {
                calculator.adjacencyMatrix[row][column] = scanner.nextInt();
                if (column == row) {
                    calculator.adjacencyMatrix[row][column] = 0;
                }
            }
        }

        calculator.calculatePageRank(numberOfPages);
    }

    public void calculatePageRank(double numberOfPages) {
        double initialRank = 1 / numberOfPages;
        double outgoingLinkCount;
        double[] previousPageRanks = new double[MAX_PAGES];
        int iteration = 1;

        System.out.printf(
            " Total Number of Nodes :%s\t Initial PageRank of All Nodes :%s%n",
            numberOfPages,
            initialRank
        );

        for (int page = 1; page <= numberOfPages; page++) {
            this.pageRanks[page] = initialRank;
        }

        System.out.print("\n Initial PageRank Values , 0th Step \n");
        for (int page = 1; page <= numberOfPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRanks[page]);
        }

        while (iteration <= 2) {
            for (int page = 1; page <= numberOfPages; page++) {
                previousPageRanks[page] = this.pageRanks[page];
                this.pageRanks[page] = 0;
            }

            for (int targetPage = 1; targetPage <= numberOfPages; targetPage++) {
                for (int sourcePage = 1; sourcePage <= numberOfPages; sourcePage++) {
                    if (this.adjacencyMatrix[sourcePage][targetPage] == 1) {
                        outgoingLinkCount = 0;
                        for (int neighborPage = 1; neighborPage <= numberOfPages; neighborPage++) {
                            if (this.adjacencyMatrix[sourcePage][neighborPage] == 1) {
                                outgoingLinkCount++;
                            }
                        }
                        this.pageRanks[targetPage] += previousPageRanks[sourcePage] * (1 / outgoingLinkCount);
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iteration);
            for (int page = 1; page <= numberOfPages; page++) {
                System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRanks[page]);
            }

            iteration++;
        }

        for (int page = 1; page <= numberOfPages; page++) {
            this.pageRanks[page] = (1 - DAMPING_FACTOR) + DAMPING_FACTOR * this.pageRanks[page];
        }

        System.out.print("\n Final Page Rank : \n");
        for (int page = 1; page <= numberOfPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRanks[page]);
        }
    }
}