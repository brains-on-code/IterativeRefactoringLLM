package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int processCount;
    private final int machineCount;
    private final int[][] runTime;
    private final int[][] transferTime;
    private final int[][] totalCost;

    public OptimalJobScheduling(int processCount, int machineCount, int[][] runTime, int[][] transferTime) {
        this.processCount = processCount;
        this.machineCount = machineCount;
        this.runTime = runTime;
        this.transferTime = transferTime;
        this.totalCost = new int[processCount][machineCount];
    }

    public void execute() {
        calculateAllCosts();
        printCostMatrix();
    }

    private void calculateAllCosts() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                totalCost[processIndex][machineIndex] = calculateCostFor(processIndex, machineIndex);
            }
        }
    }

    private int calculateCostFor(int processIndex, int machineIndex) {
        if (processIndex == 0) {
            return runTime[processIndex][machineIndex];
        }

        int[] candidateCosts = new int[machineCount];
        for (int previousMachineIndex = 0; previousMachineIndex < machineCount; previousMachineIndex++) {
            candidateCosts[previousMachineIndex] =
                totalCost[processIndex - 1][previousMachineIndex]
                    + transferTime[previousMachineIndex][machineIndex]
                    + runTime[processIndex][machineIndex];
        }
        return findMinimumValue(candidateCosts);
    }

    private int findMinimumValue(int[] values) {
        int minIndex = 0;
        for (int index = 1; index < values.length; index++) {
            if (values[index] < values[minIndex]) {
                minIndex = index;
            }
        }
        return values[minIndex];
    }

    private void printCostMatrix() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                System.out.print(totalCost[processIndex][machineIndex]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCost(int processIndex, int machineIndex) {
        return totalCost[processIndex][machineIndex];
    }
}