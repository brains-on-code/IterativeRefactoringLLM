package com.thealgorithms.others;

import java.util.Scanner;

/**
 * Implementation of the Banker's Algorithm.
 *
 * Wikipedia: https://en.wikipedia.org/wiki/Banker%27s_algorithm
 *
 * Time Complexity: O(n^2 * m)
 * Space Complexity: O(n * m)
 *
 * where:
 * n = number of processes
 * m = number of resources
 */
public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Utility class
    }

    /**
     * Computes the need matrix for each process.
     *
     * need[i][j] = max[i][j] - allocation[i][j]
     */
    static void calculateNeed(
            int[][] need,
            int[][] max,
            int[][] allocation,
            int totalProcesses,
            int totalResources
    ) {
        for (int i = 0; i < totalProcesses; i++) {
            for (int j = 0; j < totalResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    /**
     * Checks whether the system is in a safe state.
     *
     * @param processes       array of process IDs (0...n-1), size = n
     * @param available       array of available instances of each resource, size = m
     * @param max             max demand matrix, size = n x m
     * @param allocation      allocation matrix, size = n x m
     * @param totalProcesses  number of processes (n)
     * @param totalResources  number of resources (m)
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

        int count = 0;

        // Continue until all processes are finished or no safe sequence exists
        while (count < totalProcesses) {
            boolean foundProcess = false;

            for (int i = 0; i < totalProcesses; i++) {
                if (!finished[i]) {
                    int j;
                    for (j = 0; j < totalResources; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }

                    // If all needed resources can be satisfied
                    if (j == totalResources) {
                        for (int k = 0; k < totalResources; k++) {
                            work[k] += allocation[i][k];
                        }

                        safeSequence[count++] = i;
                        finished[i] = true;
                        foundProcess = true;
                    }
                }
            }

            // No process could be safely executed in this iteration
            if (!foundProcess) {
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        int numberOfProcesses = sc.nextInt();

        System.out.println("Enter total number of resources");
        int numberOfResources = sc.nextInt();

        int[] processes = new int[numberOfProcesses];
        for (int i = 0; i < numberOfProcesses; i++) {
            processes[i] = i;
        }

        System.out.println("--Enter the availability of--");
        int[] available = new int[numberOfResources];
        for (int i = 0; i < numberOfResources; i++) {
            System.out.println("resource " + i + ": ");
            available[i] = sc.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] max = new int[numberOfProcesses][numberOfResources];
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("For process " + i + ": ");
            for (int j = 0; j < numberOfResources; j++) {
                System.out.println("Enter the maximum instances of resource " + j);
                max[i][j] = sc.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocation = new int[numberOfProcesses][numberOfResources];
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("For process " + i + ": ");
            for (int j = 0; j < numberOfResources; j++) {
                System.out.println("Allocated instances of resource " + j);
                allocation[i][j] = sc.nextInt();
            }
        }

        checkSafeSystem(processes, available, max, allocation, numberOfProcesses, numberOfResources);

        sc.close();
    }
}

/*
Example:
n = 5
m = 3

Process     Allocation      Max       Available
            0   1   2    0   1   2    0   1   2

    0       0   1   0    7   5   3    3   3   2
    1       2   0   0    3   2   2
    2       3   0   2    9   0   2
    3       2   1   1    2   2   2
    4       0   0   2    4   3   3

Result: The system is in safe sequence and the sequence is as follows: P1, P3, P4, P0, P2
*/