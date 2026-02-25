package com.thealgorithms.others;

import java.util.Scanner;

class PageRankCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the Number of WebPages: ");
        int numberOfPages = scanner.nextInt();

        PageRankCalculator calculator = new PageRankCalculator();

        System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: ");
        for (int row = 1; row <= numberOfPages; row++) {
            for (int col = 1; col <= numberOfPages; col++) {
                calculator.adjacencyMatrix[row][col] = scanner.nextInt();
                if (col == row) {
                    calculator.adjacencyMatrix[row][col] = 0;
                }
            }
        }

        calculator.calculatePageRank(numberOfPages);
    }

    public int[][] adjacencyMatrix = new int[10][10];
    public double[] pageRank = new double[10];

    public void calculatePageRank(double numberOfPages) {
        double initialPageRank;
        double outgoingLinksCount;
        double dampingFactor = 0.85;
        double[] previousPageRank = new double[10];
        int iteration = 1;

        initialPageRank = 1 / numberOfPages;
        System.out.printf(
            " Total Number of Nodes :%s\t Initial PageRank of All Nodes :%s%n",
            numberOfPages,
            initialPageRank
        );

        for (int page = 1; page <= numberOfPages; page++) {
            this.pageRank[page] = initialPageRank;
        }

        System.out.print("\n Initial PageRank Values , 0th Step \n");
        for (int page = 1; page <= numberOfPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRank[page]);
        }

        while (iteration <= 2) {
            for (int page = 1; page <= numberOfPages; page++) {
                previousPageRank[page] = this.pageRank[page];
                this.pageRank[page] = 0;
            }

            for (int targetPage = 1; targetPage <= numberOfPages; targetPage++) {
                for (int sourcePage = 1; sourcePage <= numberOfPages; sourcePage++) {
                    if (this.adjacencyMatrix[sourcePage][targetPage] == 1) {
                        outgoingLinksCount = 0;
                        for (int k = 1; k <= numberOfPages; k++) {
                            if (this.adjacencyMatrix[sourcePage][k] == 1) {
                                outgoingLinksCount++;
                            }
                        }
                        this.pageRank[targetPage] += previousPageRank[sourcePage] * (1 / outgoingLinksCount);
                    }
                }
            }

            System.out.printf("%n After %dth Step %n", iteration);
            for (int page = 1; page <= numberOfPages; page++) {
                System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRank[page]);
            }

            iteration++;
        }

        for (int page = 1; page <= numberOfPages; page++) {
            this.pageRank[page] = (1 - dampingFactor) + dampingFactor * this.pageRank[page];
        }

        System.out.print("\n Final Page Rank : \n");
        for (int page = 1; page <= numberOfPages; page++) {
            System.out.printf(" Page Rank of %d is :\t%f%n", page, this.pageRank[page]);
        }
    }
}