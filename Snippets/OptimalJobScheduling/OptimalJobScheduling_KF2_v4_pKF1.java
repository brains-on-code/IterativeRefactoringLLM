package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int jobCount;
    private final int machineCount;
    private final int[][] processingTimeMatrix;
    private final int[][] transferTimeMatrix;
    private final int[][] cumulativeCostMatrix;

    public OptimalJobScheduling(
            int jobCount,
            int machineCount,
            int[][] processingTimeMatrix,
            int[][] transferTimeMatrix) {
        this.jobCount = jobCount;
        this.machineCount = machineCount;
        this.processingTimeMatrix = processingTimeMatrix;
        this.transferTimeMatrix = transferTimeMatrix;
        this.cumulativeCostMatrix = new int[jobCount][machineCount];
    }

    public void execute() {
        computeAllCumulativeCosts();
        printCumulativeCostMatrix();
    }

    private void computeAllCumulativeCosts() {
        for (int jobIndex = 0; jobIndex < jobCount; jobIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                cumulativeCostMatrix[jobIndex][machineIndex] =
                        computeCumulativeCostForJobOnMachine(jobIndex, machineIndex);
            }
        }
    }

    private int computeCumulativeCostForJobOnMachine(int jobIndex, int machineIndex) {
        if (jobIndex == 0) {
            return processingTimeMatrix[jobIndex][machineIndex];
        }

        int[] candidateCosts = new int[machineCount];
        for (int previousMachineIndex = 0; previousMachineIndex < machineCount; previousMachineIndex++) {
            candidateCosts[previousMachineIndex] =
                    cumulativeCostMatrix[jobIndex - 1][previousMachineIndex]
                            + transferTimeMatrix[previousMachineIndex][machineIndex]
                            + processingTimeMatrix[jobIndex][machineIndex];
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
        for (int jobIndex = 0; jobIndex < jobCount; jobIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                System.out.print(cumulativeCostMatrix[jobIndex][machineIndex]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCost(int jobIndex, int machineIndex) {
        return cumulativeCostMatrix[jobIndex][machineIndex];
    }
}