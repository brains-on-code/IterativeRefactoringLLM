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
    static void computeNeedMatrix(int[][] need, int[][] max, int[][] allocation, int processCount, int resourceCount) {
        for (int process = 0; process < processCount; process++) {
            for (int resource = 0; resource < resourceCount; resource++) {
                need[process][resource] = max[process][resource] - allocation[process][resource];
            }
        }
    }

    /**
     * Runs the safety algorithm to determine if the system is in a safe state.
     *
     * @param processes      process identifiers
     * @param available      available resources vector
     * @param max            maximum demand matrix
     * @param allocation     allocation matrix
     * @param processCount   number of processes
     * @param resourceCount  number of resources
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean isSystemInSafeState(
            int[] processes,
            int[] available,
            int[][] max,
            int[][] allocation,
            int processCount,
            int resourceCount
    ) {
        int[][] need = new int[processCount][resourceCount];
        computeNeedMatrix(need, max, allocation, processCount, resourceCount);

        boolean[] finished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int count = 0;

        while (count < processCount) {
            boolean foundProcess = false;

            for (int p = 0; p < processCount; p++) {
                if (!finished[p]) {
                    int resourceIndex;
                    for (resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                        if (need[p][resourceIndex] > work[resourceIndex]) {
                            break;
                        }
                    }

                    if (resourceIndex == resourceCount) {
                        for (int r = 0; r < resourceCount; r++) {
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

        int[] processes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            processes[i] = i;
        }

        System.out.println("--Enter the availability of--");
        int[] available = new int[resourceCount];
        for (int r = 0; r < resourceCount; r++) {
            System.out.println("resource " + r + ": ");
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

        isSystemInSafeState(processes, available, max, allocation, processCount, resourceCount);

        scanner.close();
    }
}