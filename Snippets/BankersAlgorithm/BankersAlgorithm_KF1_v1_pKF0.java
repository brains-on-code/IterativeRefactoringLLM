package com.thealgorithms.others;

import java.util.Scanner;

/**
 * Implementation of the Banker's Algorithm for deadlock avoidance.
 */
public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Utility class
    }

    /**
     * Computes the need matrix as: need = max - allocation.
     *
     * @param need       resulting need matrix
     * @param max        maximum demand matrix
     * @param allocation allocation matrix
     * @param processes  number of processes
     * @param resources  number of resources
     */
    static void calculateNeedMatrix(
            int[][] need,
            int[][] max,
            int[][] allocation,
            int processes,
            int resources
    ) {
        for (int process = 0; process < processes; process++) {
            for (int resource = 0; resource < resources; resource++) {
                need[process][resource] = max[process][resource] - allocation[process][resource];
            }
        }
    }

    /**
     * Runs the Banker's safety algorithm to determine if the system is in a safe state.
     *
     * @param processesIds array of process IDs
     * @param available    available resources vector
     * @param max          maximum demand matrix
     * @param allocation   allocation matrix
     * @param processes    number of processes
     * @param resources    number of resources
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean isSystemInSafeState(
            int[] processesIds,
            int[] available,
            int[][] max,
            int[][] allocation,
            int processes,
            int resources
    ) {
        int[][] need = new int[processes][resources];
        calculateNeedMatrix(need, max, allocation, processes, resources);

        boolean[] finished = new boolean[processes];
        int[] safeSequence = new int[processes];

        int[] work = new int[resources];
        System.arraycopy(available, 0, work, 0, resources);

        int count = 0;

        while (count < processes) {
            boolean foundProcess = false;

            for (int p = 0; p < processes; p++) {
                if (!finished[p]) {
                    int resourceIndex;
                    for (resourceIndex = 0; resourceIndex < resources; resourceIndex++) {
                        if (need[p][resourceIndex] > work[resourceIndex]) {
                            break;
                        }
                    }

                    if (resourceIndex == resources) {
                        for (int r = 0; r < resources; r++) {
                            work[r] += allocation[p][r];
                        }

                        safeSequence[count++] = p;
                        finished[p] = true;
                        foundProcess = true;
                    }
                }
            }

            if (!foundProcess) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int i = 0; i < processes; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

    /**
     * Entry point: reads input and runs the Banker's Algorithm.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        int processCount = scanner.nextInt();

        System.out.println("Enter total number of resources");
        int resourceCount = scanner.nextInt();

        int[] processesIds = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            processesIds[i] = i;
        }

        System.out.println("--Enter the availability of resources--");
        int[] available = new int[resourceCount];
        for (int r = 0; r < resourceCount; r++) {
            System.out.println("Resource " + r + ": ");
            available[r] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] max = new int[processCount][resourceCount];
        for (int p = 0; p < processCount; p++) {
            System.out.println("For process " + p + ": ");
            for (int r = 0; r < resourceCount; r++) {
                System.out.println("Enter the maximum instances of resource " + r);
                max[p][r] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocation = new int[processCount][resourceCount];
        for (int p = 0; p < processCount; p++) {
            System.out.println("For process " + p + ": ");
            for (int r = 0; r < resourceCount; r++) {
                System.out.println("Allocated instances of resource " + r);
                allocation[p][r] = scanner.nextInt();
            }
        }

        isSystemInSafeState(processesIds, available, max, allocation, processCount, resourceCount);

        scanner.close();
    }
}