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
        // Prevent instantiation
    }

    /**
     * Computes the need matrix:
     * need[i][j] = max[i][j] - allocation[i][j]
     */
    static void computeNeedMatrix(
        int[][] need,
        int[][] max,
        int[][] allocation,
        int processes,
        int resources
    ) {
        for (int process = 0; process < processes; process++) {
            for (int resource = 0; resource < resources; resource++) {
                need[process][resource] =
                    max[process][resource] - allocation[process][resource];
            }
        }
    }

    /**
     * Runs the Banker's safety algorithm to determine if the system is in a safe state.
     *
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean isSystemInSafeState(
        int[] processIds,
        int[] available,
        int[][] max,
        int[][] allocation,
        int processes,
        int resources
    ) {
        int[][] need = new int[processes][resources];
        computeNeedMatrix(need, max, allocation, processes, resources);

        boolean[] finished = new boolean[processes];
        int[] safeSequence = new int[processes];

        int[] work = new int[resources];
        System.arraycopy(available, 0, work, 0, resources);

        int completedProcesses = 0;

        while (completedProcesses < processes) {
            boolean foundProcess = false;

            for (int process = 0; process < processes; process++) {
                if (finished[process]) {
                    continue;
                }

                int resource;
                for (resource = 0; resource < resources; resource++) {
                    if (need[process][resource] > work[resource]) {
                        break;
                    }
                }

                if (resource == resources) {
                    for (int r = 0; r < resources; r++) {
                        work[r] += allocation[process][r];
                    }

                    safeSequence[completedProcesses++] = process;
                    finished[process] = true;
                    foundProcess = true;
                }
            }

            if (!foundProcess) {
                System.out.print(
                    "The system is not in the safe state because of lack of resources"
                );
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
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        int processes = scanner.nextInt();

        System.out.println("Enter total number of resources");
        int resources = scanner.nextInt();

        int[] processIds = new int[processes];
        for (int i = 0; i < processes; i++) {
            processIds[i] = i;
        }

        System.out.println("--Enter the availability of--");
        int[] available = new int[resources];
        for (int resource = 0; resource < resources; resource++) {
            System.out.println("resource " + resource + ": ");
            available[resource] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] max = new int[processes][resources];
        for (int process = 0; process < processes; process++) {
            System.out.println("For process " + process + ": ");
            for (int resource = 0; resource < resources; resource++) {
                System.out.println("Enter the maximum instances of resource " + resource);
                max[process][resource] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocation = new int[processes][resources];
        for (int process = 0; process < processes; process++) {
            System.out.println("For process " + process + ": ");
            for (int resource = 0; resource < resources; resource++) {
                System.out.println("Allocated instances of resource " + resource);
                allocation[process][resource] = scanner.nextInt();
            }
        }

        isSystemInSafeState(processIds, available, max, allocation, processes, resources);

        scanner.close();
    }
}