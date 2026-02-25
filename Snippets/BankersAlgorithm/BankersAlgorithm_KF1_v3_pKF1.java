package com.thealgorithms.others;

import java.util.Scanner;

/**
 * Implementation of Banker's Algorithm for deadlock avoidance.
 */
public final class BankersAlgorithm {

    private BankersAlgorithm() {
    }

    /**
     * Computes the need matrix as: need = max - allocation.
     */
    static void computeNeedMatrix(
            int[][] needMatrix,
            int[][] maxDemandMatrix,
            int[][] allocationMatrix,
            int numberOfProcesses,
            int numberOfResources
    ) {
        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            for (int resourceIndex = 0; resourceIndex < numberOfResources; resourceIndex++) {
                needMatrix[processIndex][resourceIndex] =
                        maxDemandMatrix[processIndex][resourceIndex] - allocationMatrix[processIndex][resourceIndex];
            }
        }
    }

    /**
     * Runs the safety algorithm to determine if the system is in a safe state.
     *
     * @param processIds        process identifiers
     * @param availableResources available resources vector
     * @param maxDemandMatrix   maximum demand matrix
     * @param allocationMatrix  allocation matrix
     * @param numberOfProcesses number of processes
     * @param numberOfResources number of resources
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean isSystemInSafeState(
            int[] processIds,
            int[] availableResources,
            int[][] maxDemandMatrix,
            int[][] allocationMatrix,
            int numberOfProcesses,
            int numberOfResources
    ) {
        int[][] needMatrix = new int[numberOfProcesses][numberOfResources];
        computeNeedMatrix(needMatrix, maxDemandMatrix, allocationMatrix, numberOfProcesses, numberOfResources);

        boolean[] isProcessFinished = new boolean[numberOfProcesses];
        int[] safeSequence = new int[numberOfProcesses];

        int[] workResources = new int[numberOfResources];
        System.arraycopy(availableResources, 0, workResources, 0, numberOfResources);

        int completedProcessCount = 0;

        while (completedProcessCount < numberOfProcesses) {
            boolean foundExecutableProcess = false;

            for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
                if (!isProcessFinished[processIndex]) {
                    int resourceIndex;
                    for (resourceIndex = 0; resourceIndex < numberOfResources; resourceIndex++) {
                        if (needMatrix[processIndex][resourceIndex] > workResources[resourceIndex]) {
                            break;
                        }
                    }

                    if (resourceIndex == numberOfResources) {
                        for (int resource = 0; resource < numberOfResources; resource++) {
                            workResources[resource] += allocationMatrix[processIndex][resource];
                        }

                        safeSequence[completedProcessCount++] = processIndex;
                        isProcessFinished[processIndex] = true;
                        foundExecutableProcess = true;
                    }
                }
            }

            if (!foundExecutableProcess) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int sequenceIndex = 0; sequenceIndex < numberOfProcesses; sequenceIndex++) {
            System.out.print("P" + safeSequence[sequenceIndex] + " ");
        }

        return true;
    }

    /**
     * Entry point: reads input and runs Banker's Algorithm.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        int numberOfProcesses = scanner.nextInt();

        System.out.println("Enter total number of resources");
        int numberOfResources = scanner.nextInt();

        int[] processIds = new int[numberOfProcesses];
        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            processIds[processIndex] = processIndex;
        }

        System.out.println("--Enter the availability of--");
        int[] availableResources = new int[numberOfResources];
        for (int resourceIndex = 0; resourceIndex < numberOfResources; resourceIndex++) {
            System.out.println("resource " + resourceIndex + ": ");
            availableResources[resourceIndex] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] maxDemandMatrix = new int[numberOfProcesses][numberOfResources];
        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < numberOfResources; resourceIndex++) {
                System.out.println("Enter the maximum instances of resource " + resourceIndex);
                maxDemandMatrix[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocationMatrix = new int[numberOfProcesses][numberOfResources];
        for (int processIndex = 0; processIndex < numberOfProcesses; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < numberOfResources; resourceIndex++) {
                System.out.println("Allocated instances of resource " + resourceIndex);
                allocationMatrix[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        isSystemInSafeState(processIds, availableResources, maxDemandMatrix, allocationMatrix, numberOfProcesses, numberOfResources);

        scanner.close();
    }
}