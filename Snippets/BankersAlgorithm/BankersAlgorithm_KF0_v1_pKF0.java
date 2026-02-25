package com.thealgorithms.others;

import java.util.Scanner;

/**
 * Implementation of Banker's Algorithm.
 *
 * Wikipedia: https://en.wikipedia.org/wiki/Banker%27s_algorithm
 *
 * Time Complexity: O(n^2 * m)
 * Space Complexity: O(n * m)
 * where n = number of processes and m = number of resources.
 */
public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Utility class
    }

    /**
     * Computes the need matrix for each process.
     */
    static void calculateNeed(
            int[][] need,
            int[][] max,
            int[][] allocation,
            int totalProcesses,
            int totalResources
    ) {
        for (int process = 0; process < totalProcesses; process++) {
            for (int resource = 0; resource < totalResources; resource++) {
                need[process][resource] = max[process][resource] - allocation[process][resource];
            }
        }
    }

    /**
     * Checks whether the system is in a safe state.
     *
     * @param processes       array of process IDs (0...n-1), size = n
     * @param available       array of available instances of each resource, size = m
     * @param max             matrix of maximum demand of each process, size = n*m
     * @param allocation      matrix of currently allocated resources to each process, size = n*m
     * @param totalProcesses  number of processes, n
     * @param totalResources  number of resources, m
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean checkSafeSystem(
            int[] processes,
            int[] available,
            int[][] max,
            int[][] allocation,
            int totalProcesses,
            int totalResources
    ) {
        int[][] need = new int[totalProcesses][totalResources];
        calculateNeed(need, max, allocation, totalProcesses, totalResources);

        boolean[] finished = new boolean[totalProcesses];
        int[] safeSequence = new int[totalProcesses];

        int[] work = new int[totalResources];
        System.arraycopy(available, 0, work, 0, totalResources);

        int completedCount = 0;

        while (completedCount < totalProcesses) {
            boolean progressMade = false;

            for (int process = 0; process < totalProcesses; process++) {
                if (finished[process]) {
                    continue;
                }

                int resource;
                for (resource = 0; resource < totalResources; resource++) {
                    if (need[process][resource] > work[resource]) {
                        break;
                    }
                }

                if (resource == totalResources) {
                    for (int r = 0; r < totalResources; r++) {
                        work[r] += allocation[process][r];
                    }

                    safeSequence[completedCount++] = process;
                    finished[process] = true;
                    progressMade = true;
                }
            }

            if (!progressMade) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int i = 0; i < totalProcesses; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

    /**
     * Main method for running Banker's Algorithm interactively.
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter total number of processes");
            int numberOfProcesses = scanner.nextInt();

            System.out.println("Enter total number of resources");
            int numberOfResources = scanner.nextInt();

            int[] processes = new int[numberOfProcesses];
            for (int i = 0; i < numberOfProcesses; i++) {
                processes[i] = i;
            }

            System.out.println("--Enter the availability of--");
            int[] available = new int[numberOfResources];
            for (int resource = 0; resource < numberOfResources; resource++) {
                System.out.println("Resource " + resource + ": ");
                available[resource] = scanner.nextInt();
            }

            System.out.println("--Enter the maximum matrix--");
            int[][] max = new int[numberOfProcesses][numberOfResources];
            for (int process = 0; process < numberOfProcesses; process++) {
                System.out.println("For process " + process + ": ");
                for (int resource = 0; resource < numberOfResources; resource++) {
                    System.out.println("Enter the maximum instances of resource " + resource);
                    max[process][resource] = scanner.nextInt();
                }
            }

            System.out.println("--Enter the allocation matrix--");
            int[][] allocation = new int[numberOfProcesses][numberOfResources];
            for (int process = 0; process < numberOfProcesses; process++) {
                System.out.println("For process " + process + ": ");
                for (int resource = 0; resource < numberOfResources; resource++) {
                    System.out.println("Allocated instances of resource " + resource);
                    allocation[process][resource] = scanner.nextInt();
                }
            }

            checkSafeSystem(processes, available, max, allocation, numberOfProcesses, numberOfResources);
        }
    }
}