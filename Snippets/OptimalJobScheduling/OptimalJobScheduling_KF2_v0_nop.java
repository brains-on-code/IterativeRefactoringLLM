package com.thealgorithms.dynamicprogramming;


public class OptimalJobScheduling {

    private final int numberProcesses;
    private final int numberMachines;
    private final int[][] run;
    private final int[][] transfer;
    private final int[][] cost;


    public OptimalJobScheduling(int numberProcesses, int numberMachines, int[][] run, int[][] transfer) {
        this.numberProcesses = numberProcesses;
        this.numberMachines = numberMachines;
        this.run = run;
        this.transfer = transfer;
        this.cost = new int[numberProcesses][numberMachines];
    }


    public void execute() {
        this.calculateCost();
        this.showResults();
    }


    private void calculateCost() {

        for (int i = 0; i < numberProcesses; i++) {

            for (int j = 0; j < numberMachines; j++) {

                cost[i][j] = runningCost(i, j);
            }
        }
    }


    private int runningCost(int process, int machine) {

        if (process == 0) {
            return run[process][machine];
        } else {

            int[] runningCosts = new int[numberMachines];

            for (int k = 0; k < numberMachines; k++) {
                runningCosts[k] = cost[process - 1][k] + transfer[k][machine] + run[process][machine];
            }
            return findMin(runningCosts);
        }
    }


    private int findMin(int[] costArr) {

        int min = 0;

        for (int i = 1; i < costArr.length; i++) {

            if (costArr[i] < costArr[min]) {
                min = i;
            }
        }
        return costArr[min];
    }


    private void showResults() {

        for (int i = 0; i < numberProcesses; i++) {

            for (int j = 0; j < numberMachines; j++) {
                System.out.print(cost[i][j]);
                System.out.print(" ");
            }

            System.out.println();
        }
        System.out.println();
    }


    public int getCost(int process, int machine) {
        return cost[process][machine];
    }
}
