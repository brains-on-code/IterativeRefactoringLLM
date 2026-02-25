package com.thealgorithms.others;

import java.util.Scanner;

public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the need matrix as: need[i][j] = max[i][j] - allocation[i][j].
     */
    static void calculateNeed(
            int[][] need,
            int[][] max,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        for (int i = 0; i < processCount; i++) {
            for (int j = 0; j < resourceCount; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    /**
     * Checks if the system is in a safe state using the Banker's algorithm.
     *
     * @return true if the system is in a safe state; false otherwise
     */
    static boolean checkSafeSystem(
            int[] processes,
            int[] available,
            int[][] max,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        int[][] need = new int[processCount][resourceCount];
        calculateNeed(need, max, allocation, processCount, resourceCount);

        boolean[] finished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int completedCount = 0;

        while (completedCount < processCount) {
            boolean progressMade = false;

            for (int i = 0; i < processCount; i++) {
                if (finished[i]) {
                    continue;
                }

                int j;
                for (j = 0; j < resourceCount; j++) {
                    if (need[i][j] > work[j]) {
                        break;
                    }
                }

                // If all needed resources can be satisfied
                if (j == resourceCount) {
                    for (int k = 0; k < resourceCount; k++) {
                        work[k] += allocation[i][k];
                    }

                    safeSequence[completedCount++] = i;
                    finished[i] = true;
                    progressMade = true;
                }
            }

            if (!progressMade) {
                System.out.print("The system is not in a safe state due to insufficient resources.");
                return false;
            }
        }

        System.out.print("The system is in a safe state. Safe sequence: ");
        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes:");
        int processCount = scanner.nextInt();

        System.out.println("Enter total number of resources:");
        int resourceCount = scanner.nextInt();

        int[] processes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            processes[i] = i;
        }

        System.out.println("-- Enter the available instances for each resource --");
        int[] available = new int[resourceCount];
        for (int i = 0; i < resourceCount; i++) {
            System.out.print("Resource " + i + ": ");
            available[i] = scanner.nextInt();
        }

        System.out.println("-- Enter the maximum demand matrix --");
        int[][] max = new int[processCount][resourceCount];
        for (int i = 0; i < processCount; i++) {
            System.out.println("For process " + i + ":");
            for (int j = 0; j < resourceCount; j++) {
                System.out.print("Maximum instances of resource " + j + ": ");
                max[i][j] = scanner.nextInt();
            }
        }

        System.out.println("-- Enter the allocation matrix --");
        int[][] allocation = new int[processCount][resourceCount];
        for (int i = 0; i < processCount; i++) {
            System.out.println("For process " + i + ":");
            for (int j = 0; j < resourceCount; j++) {
                System.out.print("Allocated instances of resource " + j + ": ");
                allocation[i][j] = scanner.nextInt();
            }
        }

        checkSafeSystem(processes, available, max, allocation, processCount, resourceCount);

        scanner.close();
    }
}