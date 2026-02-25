package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int numberOfProcesses;
    private final int numberOfMachines;
    private final int[][] runTime;
    private final int[][] transferTime;
    private final int[][] totalCost;

    public OptimalJobScheduling(int numberOfProcesses, int numberOfMachines, int[][] runTime, int[][] transferTime) {
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfMachines = numberOfMachines;
        this.runTime = runTime;
        this.transferTime = transferTime;
        this.totalCost = new int[numberOfProcesses][numberOfMachines];
    }

    public void execute() {
        calculateAllCosts();
        printCosts();
    }

    private void calculateAllCosts() {
        for (int process = 0; process < numberOfProcesses; process++) {
            for (int machine = 0; machine < numberOfMachines; machine++) {
                totalCost[process][machine] = calculateCostFor(process, machine);
            }
        }
    }

    private int calculateCostFor(int process, int machine) {
        if (process == 0) {
            return runTime[process][machine];
        }

        int[] candidateCosts = new int[numberOfMachines];
        for (int previousMachine = 0; previousMachine < numberOfMachines; previousMachine++) {
            candidateCosts[previousMachine] =
                totalCost[process - 1][previousMachine]
                    + transferTime[previousMachine][machine]
                    + runTime[process][machine];
        }
        return minValue(candidateCosts);
    }

    private int minValue(int[] values) {
        int minIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[minIndex]) {
                minIndex = i;
            }
        }
        return values[minIndex];
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