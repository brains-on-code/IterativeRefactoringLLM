package com.thealgorithms.others;

import java.util.Scanner;

/**
 * Implementation of the Banker's Algorithm for deadlock avoidance.
 */
public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Prevent instantiation
    }

    /**
     * Computes the need matrix where:
     * need[process][resource] = max[process][resource] - allocation[process][resource]
     */
    static void calculateNeed(
            int[][] need,
            int[][] max,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        for (int process = 0; process < processCount; process++) {
            for (int resource = 0; resource < resourceCount; resource++) {
                need[process][resource] = max[process][resource] - allocation[process][resource];
            }
        }
    }

    /**
     * Runs the Banker's algorithm and prints whether the system is in a safe state.
     *
     * @return true if the system is in a safe state, false otherwise
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
            boolean progressMadeInThisPass = false;

            for (int process = 0; process < processCount; process++) {
                if (finished[process]) {
                    continue;
                }

                int resource;
                for (resource = 0; resource < resourceCount; resource++) {
                    if (need[process][resource] > work[resource]) {
                        break;
                    }
                }

                // If all needed resources for this process can be satisfied
                if (resource == resourceCount) {
                    for (int r = 0; r < resourceCount; r++) {
                        work[r] += allocation[process][r];
                    }

                    safeSequence[completedCount++] = process;
                    finished[process] = true;
                    progressMadeInThisPass = true;
                }
            }

            // If no process could be completed in this pass, the system is unsafe
            if (!progressMadeInThisPass) {
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
        for (int resource = 0; resource < resourceCount; resource++) {
            System.out.print("Resource " + resource + ": ");
            available[resource] = scanner.nextInt();
        }

        System.out.println("-- Enter the maximum demand matrix --");
        int[][] max = new int[processCount][resourceCount];
        for (int process = 0; process < processCount; process++) {
            System.out.println("For process " + process + ":");
            for (int resource = 0; resource < resourceCount; resource++) {
                System.out.print("Maximum instances of resource " + resource + ": ");
                max[process][resource] = scanner.nextInt();
            }
        }

        System.out.println("-- Enter the allocation matrix --");
        int[][] allocation = new int[processCount][resourceCount];
        for (int process = 0; process < processCount; process++) {
            System.out.println("For process " + process + ":");
            for (int resource = 0; resource < resourceCount; resource++) {
                System.out.print("Allocated instances of resource " + resource + ": ");
                allocation[process][resource] = scanner.nextInt();
            }
        }

        checkSafeSystem(processes, available, max, allocation, processCount, resourceCount);

        scanner.close();
    }
}