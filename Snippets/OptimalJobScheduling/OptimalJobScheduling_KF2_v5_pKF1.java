package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int numberOfJobs;
    private final int numberOfMachines;
    private final int[][] processingTimes;
    private final int[][] transferTimes;
    private final int[][] cumulativeCosts;

    public OptimalJobScheduling(
            int numberOfJobs,
            int numberOfMachines,
            int[][] processingTimes,
            int[][] transferTimes) {
        this.numberOfJobs = numberOfJobs;
        this.numberOfMachines = numberOfMachines;
        this.processingTimes = processingTimes;
        this.transferTimes = transferTimes;
        this.cumulativeCosts = new int[numberOfJobs][numberOfMachines];
    }

    public void execute() {
        computeAllCumulativeCosts();
        printCumulativeCostMatrix();
    }

    private void computeAllCumulativeCosts() {
        for (int jobIndex = 0; jobIndex < numberOfJobs; jobIndex++) {
            for (int machineIndex = 0; machineIndex < numberOfMachines; machineIndex++) {
                cumulativeCosts[jobIndex][machineIndex] =
                        computeCumulativeCost(jobIndex, machineIndex);
            }
        }
    }

    private int computeCumulativeCost(int jobIndex, int machineIndex) {
        if (jobIndex == 0) {
            return processingTimes[jobIndex][machineIndex];
        }

        int[] candidateCosts = new int[numberOfMachines];
        for (int previousMachineIndex = 0; previousMachineIndex < numberOfMachines; previousMachineIndex++) {
            candidateCosts[previousMachineIndex] =
                    cumulativeCosts[jobIndex - 1][previousMachineIndex]
                            + transferTimes[previousMachineIndex][machineIndex]
                            + processingTimes[jobIndex][machineIndex];
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
        for (int jobIndex = 0; jobIndex < numberOfJobs; jobIndex++) {
            for (int machineIndex = 0; machineIndex < numberOfMachines; machineIndex++) {
                System.out.print(cumulativeCosts[jobIndex][machineIndex]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCost(int jobIndex, int machineIndex) {
        return cumulativeCosts[jobIndex][machineIndex];
    }
}