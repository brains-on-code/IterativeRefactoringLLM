package com.thealgorithms.others;

import java.util.Scanner;

public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Utility class; prevent instantiation
    }

    /**
     * need[i][j] = max[i][j] - allocation[i][j]
     */
    static void calculateNeed(
            int[][] need,
            int[][] max,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        for (int p = 0; p < processCount; p++) {
            for (int r = 0; r < resourceCount; r++) {
                need[p][r] = max[p][r] - allocation[p][r];
            }
        }
    }

    /**
     * Banker's algorithm: returns true if the system is in a safe state.
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

            for (int p = 0; p < processCount; p++) {
                if (finished[p]) {
                    continue;
                }

                int r;
                for (r = 0; r < resourceCount; r++) {
                    if (need[p][r] > work[r]) {
                        break;
                    }
                }

                if (r == resourceCount) {
                    for (int k = 0; k < resourceCount; k++) {
                        work[k] += allocation[p][k];
                    }

                    safeSequence[completedCount++] = p;
                    finished[p] = true;
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
        for (int r = 0; r < resourceCount; r++) {
            System.out.print("Resource " + r + ": ");
            available[r] = scanner.nextInt();
        }

        System.out.println("-- Enter the maximum demand matrix --");
        int[][] max = new int[processCount][resourceCount];
        for (int p = 0; p < processCount; p++) {
            System.out.println("For process " + p + ":");
            for (int r = 0; r < resourceCount; r++) {
                System.out.print("Maximum instances of resource " + r + ": ");
                max[p][r] = scanner.nextInt();
            }
        }

        System.out.println("-- Enter the allocation matrix --");
        int[][] allocation = new int[processCount][resourceCount];
        for (int p = 0; p < processCount; p++) {
            System.out.println("For process " + p + ":");
            for (int r = 0; r < resourceCount; r++) {
                System.out.print("Allocated instances of resource " + r + ": ");
                allocation[p][r] = scanner.nextInt();
            }
        }

        checkSafeSystem(processes, available, max, allocation, processCount, resourceCount);

        scanner.close();
    }
}