package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int processCount;
    private final int machineCount;
    private final int[][] processingTimes;
    private final int[][] transferTimes;
    private final int[][] cumulativeCosts;

    public OptimalJobScheduling(
            int processCount,
            int machineCount,
            int[][] processingTimes,
            int[][] transferTimes) {
        this.processCount = processCount;
        this.machineCount = machineCount;
        this.processingTimes = processingTimes;
        this.transferTimes = transferTimes;
        this.cumulativeCosts = new int[processCount][machineCount];
    }

    public void execute() {
        computeAllCumulativeCosts();
        printCumulativeCostMatrix();
    }

    private void computeAllCumulativeCosts() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                cumulativeCosts[processIndex][machineIndex] =
                        computeCumulativeCostForProcessOnMachine(processIndex, machineIndex);
            }
        }
    }

    private int computeCumulativeCostForProcessOnMachine(int processIndex, int machineIndex) {
        if (processIndex == 0) {
            return processingTimes[processIndex][machineIndex];
        }

        int[] candidateCosts = new int[machineCount];
        for (int previousMachineIndex = 0; previousMachineIndex < machineCount; previousMachineIndex++) {
            candidateCosts[previousMachineIndex] =
                    cumulativeCosts[processIndex - 1][previousMachineIndex]
                            + transferTimes[previousMachineIndex][machineIndex]
                            + processingTimes[processIndex][machineIndex];
        }
        return findMinimum(candidateCosts);
    }

    private int findMinimum(int[] values) {
        int minIndex = 0;
        for (int index = 1; index < values.length; index++) {
            if (values[index] < values[minIndex]) {
                minIndex = index;
            }
        }
        return values[minIndex];
    }

    private void printCumulativeCostMatrix() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                System.out.print(cumulativeCosts[processIndex][machineIndex]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCost(int processIndex, int machineIndex) {
        return cumulativeCosts[processIndex][machineIndex];
    }
}