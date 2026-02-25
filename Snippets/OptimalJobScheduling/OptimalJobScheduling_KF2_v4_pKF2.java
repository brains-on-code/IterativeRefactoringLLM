package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int numberOfProcesses;
    private final int numberOfMachines;
    private final int[][] runTime;
    private final int[][] transferTime;
    private final int[][] totalCost;

    public OptimalJobScheduling(
        int numberOfProcesses,
        int numberOfMachines,
        int[][] runTime,
        int[][] transferTime
    ) {
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfMachines = numberOfMachines;
        this.runTime = runTime;
        this.transferTime = transferTime;
        this.totalCost = new int[numberOfProcesses][numberOfMachines];
    }

    public void execute() {
        computeAllCosts();
        printCosts();
    }

    private void computeAllCosts() {
        for (int process = 0; process < numberOfProcesses; process++) {
            for (int machine = 0; machine < numberOfMachines; machine++) {
                totalCost[process][machine] = computeCost(process, machine);
            }
        }
    }

    private int computeCost(int process, int machine) {
        if (process == 0) {
            return runTime[process][machine];
        }

        int minCost = Integer.MAX_VALUE;
        int previousProcess = process - 1;

        for (int previousMachine = 0; previousMachine < numberOfMachines; previousMachine++) {
            int candidateCost =
                totalCost[previousProcess][previousMachine]
                    + transferTime[previousMachine][machine]
                    + runTime[process][machine];

            if (candidateCost < minCost) {
                minCost = candidateCost;
            }
        }

        return minCost;
    }

    private void printCosts() {
        for (int process = 0; process < numberOfProcesses; process++) {
            for (int machine = 0; machine < numberOfMachines; machine++) {
                System.out.print(totalCost[process][machine] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCost(int process, int machine) {
        return totalCost[process][machine];
    }
}