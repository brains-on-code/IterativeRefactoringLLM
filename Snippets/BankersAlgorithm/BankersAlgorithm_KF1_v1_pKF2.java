package com.thealgorithms.others;

import java.util.Scanner;

/**
 * Implementation of the Banker's Algorithm for deadlock avoidance.
 *
 * Given:
 * - Number of processes
 * - Number of resource types
 * - Available resources vector
 * - Maximum demand matrix
 * - Allocation matrix
 *
 * The algorithm determines whether the system is in a safe state and, if so,
 * prints a safe sequence of process execution.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Need matrix as:
     * need[i][j] = max[i][j] - allocation[i][j]
     *
     * @param need       resulting need matrix
     * @param max        maximum demand matrix
     * @param allocation allocation matrix
     * @param processes  number of processes
     * @param resources  number of resource types
     */
    static void method1(int[][] need, int[][] max, int[][] allocation, int processes, int resources) {
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    /**
     * Runs the Banker's safety algorithm to determine if the system is in a safe state.
     *
     * @param processesIds array of process IDs (0..n-1)
     * @param available    available resources vector
     * @param max          maximum demand matrix
     * @param allocation   allocation matrix
     * @param processes    number of processes
     * @param resources    number of resource types
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean method2(
        int[] processesIds,
        int[] available,
        int[][] max,
        int[][] allocation,
        int processes,
        int resources
    ) {
        int[][] need = new int[processes][resources];
        method1(need, max, allocation, processes, resources);

        boolean[] finished = new boolean[processes];
        int[] safeSequence = new int[processes];

        int[] work = new int[resources];
        System.arraycopy(available, 0, work, 0, resources);

        int count = 0;

        while (count < processes) {
            boolean foundProcess = false;

            for (int i = 0; i < processes; i++) {
                if (!finished[i]) {
                    int j;
                    for (j = 0; j < resources; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }

                    // If all needs of process i can be satisfied
                    if (j == resources) {
                        for (int k = 0; k < resources; k++) {
                            work[k] += allocation[i][k];
                        }

                        safeSequence[count++] = i;
                        finished[i] = true;
                        foundProcess = true;
                    }
                }
            }

            // No process could be found in this pass -> unsafe state
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
     * Entry point: reads input from standard input and runs the Banker's Algorithm.
     *
     * Expected input:
     * - Number of processes
     * - Number of resources
     * - Available vector
     * - Maximum matrix
     * - Allocation matrix
     *
     * @param args command-line arguments (unused)
     */
    public static void method3(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        int processes = scanner.nextInt();

        System.out.println("Enter total number of resources");
        int resources = scanner.nextInt();

        int[] processesIds = new int[processes];
        for (int i = 0; i < processes; i++) {
            processesIds[i] = i;
        }

        System.out.println("--Enter the availability of--");
        int[] available = new int[resources];
        for (int j = 0; j < resources; j++) {
            System.out.println("resource " + j + ": ");
            available[j] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] max = new int[processes][resources];
        for (int i = 0; i < processes; i++) {
            System.out.println("For process " + i + ": ");
            for (int j = 0; j < resources; j++) {
                System.out.println("Enter the maximum instances of resource " + j);
                max[i][j] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocation = new int[processes][resources];
        for (int i = 0; i < processes; i++) {
            System.out.println("For process " + i + ": ");
            for (int j = 0; j < resources; j++) {
                System.out.println("Allocated instances of resource " + j);
                allocation[i][j] = scanner.nextInt();
            }
        }

        method2(processesIds, available, max, allocation, processes, resources);

        scanner.close();
    }
}