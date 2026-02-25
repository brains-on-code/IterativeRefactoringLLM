package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int numberOfProcesses;
    private final int numberOfMachines;
    private final int[][] processingTimes;
    private final int[][] transferTimes;
    private final int[][] cumulativeCosts;

    public OptimalJobScheduling(int numberOfProcesses, int numberOfMachines, int[][] processingTimes, int[][] transferTimes) {
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfMachines = numberOfMachines;
        this.processingTimes = processingTimes;
        this.transferTimes = transferTimes;
        this.cumulativeCosts = new int[numberOfProcesses][numberOfMachines];
    }

    public void execute() {
        computeAllCumulativeCosts();
        printCumulativeCostMatrix();
    }

    private void computeAllCumulativeCosts() {
        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            for (int machineIndex = 0; machineIndex < numberOfMachines; machineIndex++) {
                cumulativeCosts[processIndex][machineIndex] = computeCumulativeCost(processIndex, machineIndex);
            }
        }
    }

    private int computeCumulativeCost(int processIndex, int machineIndex) {
        if (processIndex == 0) {
            return processingTimes[processIndex][machineIndex];
        }

        int[] candidateCosts = new int[numberOfMachines];
        for (int previousMachineIndex = 0; previousMachineIndex < numberOfMachines; previousMachineIndex++) {
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
        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            for (int machineIndex = 0; machineIndex < numberOfMachines; machineIndex++) {
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