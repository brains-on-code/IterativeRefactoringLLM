package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int processCount;
    private final int machineCount;
    private final int[][] runTime;
    private final int[][] transferTime;
    private final int[][] cost;

    public OptimalJobScheduling(
            int processCount,
            int machineCount,
            int[][] runTime,
            int[][] transferTime
    ) {
        this.processCount = processCount;
        this.machineCount = machineCount;
        this.runTime = runTime;
        this.transferTime = transferTime;
        this.cost = new int[processCount][machineCount];
    }

    public void execute() {
        calculateCosts();
        printCosts();
    }

    private void calculateCosts() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                cost[processIndex][machineIndex] = computeRunningCost(processIndex, machineIndex);
            }
        }
    }

    private int computeRunningCost(int processIndex, int machineIndex) {
        if (processIndex == 0) {
            return runTime[processIndex][machineIndex];
        }

        int minCost = Integer.MAX_VALUE;
        int currentRunTime = runTime[processIndex][machineIndex];

        for (int previousMachineIndex = 0; previousMachineIndex < machineCount; previousMachineIndex++) {
            int totalCost =
                    cost[processIndex - 1][previousMachineIndex]
                            + transferTime[previousMachineIndex][machineIndex]
                            + currentRunTime;

            if (totalCost < minCost) {
                minCost = totalCost;
            }
        }

        return minCost;
    }

    private void printCosts() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                System.out.print(cost[processIndex][machineIndex] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCost(int processIndex, int machineIndex) {
        return cost[processIndex][machineIndex];
    }
}