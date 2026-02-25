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
            int processCount,
            int resourceCount
    ) {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                needMatrix[processIndex][resourceIndex] =
                        maxDemandMatrix[processIndex][resourceIndex] - allocationMatrix[processIndex][resourceIndex];
            }
        }
    }

    /**
     * Runs the safety algorithm to determine if the system is in a safe state.
     *
     * @param processIds     process identifiers
     * @param available      available resources vector
     * @param maxDemand      maximum demand matrix
     * @param allocation     allocation matrix
     * @param processCount   number of processes
     * @param resourceCount  number of resources
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean isSystemInSafeState(
            int[] processIds,
            int[] available,
            int[][] maxDemand,
            int[][] allocation,
            int processCount,
            int resourceCount
    ) {
        int[][] needMatrix = new int[processCount][resourceCount];
        computeNeedMatrix(needMatrix, maxDemand, allocation, processCount, resourceCount);

        boolean[] isProcessFinished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int completedProcessCount = 0;

        while (completedProcessCount < processCount) {
            boolean foundExecutableProcess = false;

            for (int processIndex = 0; processIndex < processCount; processIndex++) {
                if (!isProcessFinished[processIndex]) {
                    int resourceIndex;
                    for (resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                        if (needMatrix[processIndex][resourceIndex] > work[resourceIndex]) {
                            break;
                        }
                    }

                    if (resourceIndex == resourceCount) {
                        for (int r = 0; r < resourceCount; r++) {
                            work[r] += allocation[processIndex][r];
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
        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

    /**
     * Entry point: reads input and runs Banker's Algorithm.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        int processCount = scanner.nextInt();

        System.out.println("Enter total number of resources");
        int resourceCount = scanner.nextInt();

        int[] processIds = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            processIds[i] = i;
        }

        System.out.println("--Enter the availability of--");
        int[] availableResources = new int[resourceCount];
        for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
            System.out.println("resource " + resourceIndex + ": ");
            availableResources[resourceIndex] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] maxDemandMatrix = new int[processCount][resourceCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                System.out.println("Enter the maximum instances of resource " + resourceIndex);
                maxDemandMatrix[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocationMatrix = new int[processCount][resourceCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                System.out.println("Allocated instances of resource " + resourceIndex);
                allocationMatrix[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        isSystemInSafeState(processIds, availableResources, maxDemandMatrix, allocationMatrix, processCount, resourceCount);

        scanner.close();
    }
}