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
            int[][] need,
            int[][] maxDemand,
            int[][] allocation,
            int processCount,
            int resourceCount
    ) {
        for (int process = 0; process < processCount; process++) {
            for (int resource = 0; resource < resourceCount; resource++) {
                need[process][resource] = maxDemand[process][resource] - allocation[process][resource];
            }
        }
    }

    /**
     * Runs the safety algorithm to determine if the system is in a safe state.
     *
     * @param processIds         process identifiers
     * @param available          available resources vector
     * @param maxDemand          maximum demand matrix
     * @param allocation         allocation matrix
     * @param processCount       number of processes
     * @param resourceCount      number of resources
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
        int[][] need = new int[processCount][resourceCount];
        computeNeedMatrix(need, maxDemand, allocation, processCount, resourceCount);

        boolean[] finished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int completedProcesses = 0;

        while (completedProcesses < processCount) {
            boolean foundProcessToExecute = false;

            for (int process = 0; process < processCount; process++) {
                if (!finished[process]) {
                    int resource;
                    for (resource = 0; resource < resourceCount; resource++) {
                        if (need[process][resource] > work[resource]) {
                            break;
                        }
                    }

                    if (resource == resourceCount) {
                        for (int r = 0; r < resourceCount; r++) {
                            work[r] += allocation[process][r];
                        }

                        safeSequence[completedProcesses++] = process;
                        finished[process] = true;
                        foundProcessToExecute = true;
                    }
                }
            }

            if (!foundProcessToExecute) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int index = 0; index < processCount; index++) {
            System.out.print("P" + safeSequence[index] + " ");
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
        for (int process = 0; process < processCount; process++) {
            processIds[process] = process;
        }

        System.out.println("--Enter the availability of--");
        int[] available = new int[resourceCount];
        for (int resource = 0; resource < resourceCount; resource++) {
            System.out.println("resource " + resource + ": ");
            available[resource] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] maxDemand = new int[processCount][resourceCount];
        for (int process = 0; process < processCount; process++) {
            System.out.println("For process " + process + ": ");
            for (int resource = 0; resource < resourceCount; resource++) {
                System.out.println("Enter the maximum instances of resource " + resource);
                maxDemand[process][resource] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocation = new int[processCount][resourceCount];
        for (int process = 0; process < processCount; process++) {
            System.out.println("For process " + process + ": ");
            for (int resource = 0; resource < resourceCount; resource++) {
                System.out.println("Allocated instances of resource " + resource);
                allocation[process][resource] = scanner.nextInt();
            }
        }

        isSystemInSafeState(processIds, available, maxDemand, allocation, processCount, resourceCount);

        scanner.close();
    }
}