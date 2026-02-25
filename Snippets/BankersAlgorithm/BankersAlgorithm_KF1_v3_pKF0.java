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
     * @param need          resulting need matrix
     * @param max           maximum demand matrix
     * @param allocation    allocation matrix
     * @param processCount  number of processes
     * @param resourceCount number of resources
     */
    static void calculateNeedMatrix(
        int[][] need,
        int[][] max,
        int[][] allocation,
        int processCount,
        int resourceCount
    ) {
        for (int process = 0; process < processCount; process++) {
            for (int resource = 0; resource < resourceCount; resource++) {
                need[process][resource] = max[process][resource] - allocation[process][resource];
            }
        }
    }

    /**
     * Runs the Banker's safety algorithm to determine if the system is in a safe state.
     *
     * @param processIds    array of process IDs
     * @param available     available resources vector
     * @param max           maximum demand matrix
     * @param allocation    allocation matrix
     * @param processCount  number of processes
     * @param resourceCount number of resources
     * @return true if the system is in a safe state, false otherwise
     */
    static boolean isSystemInSafeState(
        int[] processIds,
        int[] available,
        int[][] max,
        int[][] allocation,
        int processCount,
        int resourceCount
    ) {
        int[][] need = new int[processCount][resourceCount];
        calculateNeedMatrix(need, max, allocation, processCount, resourceCount);

        boolean[] finished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int completedProcesses = 0;

        while (completedProcesses < processCount) {
            boolean foundProcess = false;

            for (int process = 0; process < processCount; process++) {
                if (finished[process]) {
                    continue;
                }

                if (canProcessBeSatisfied(need[process], work, resourceCount)) {
                    allocateResourcesBack(work, allocation[process], resourceCount);
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
        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

    private static boolean canProcessBeSatisfied(int[] needRow, int[] work, int resourceCount) {
        for (int resource = 0; resource < resourceCount; resource++) {
            if (needRow[resource] > work[resource]) {
                return false;
            }
        }
        return true;
    }

    private static void allocateResourcesBack(int[] work, int[] allocationRow, int resourceCount) {
        for (int resource = 0; resource < resourceCount; resource++) {
            work[resource] += allocationRow[resource];
        }
    }

    /**
     * Entry point: reads input and runs the Banker's Algorithm.
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Enter total number of processes");
            int processCount = scanner.nextInt();

            System.out.println("Enter total number of resources");
            int resourceCount = scanner.nextInt();

            int[] processIds = initializeProcessIds(processCount);

            int[] available = readAvailableResources(scanner, resourceCount);

            int[][] max = readMatrix(scanner, processCount, resourceCount, "maximum");

            int[][] allocation = readMatrix(scanner, processCount, resourceCount, "allocation");

            isSystemInSafeState(processIds, available, max, allocation, processCount, resourceCount);
        }
    }

    private static int[] initializeProcessIds(int processCount) {
        int[] processIds = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            processIds[i] = i;
        }
        return processIds;
    }

    private static int[] readAvailableResources(Scanner scanner, int resourceCount) {
        System.out.println("--Enter the availability of resources--");
        int[] available = new int[resourceCount];
        for (int resource = 0; resource < resourceCount; resource++) {
            System.out.println("Resource " + resource + ": ");
            available[resource] = scanner.nextInt();
        }
        return available;
    }

    private static int[][] readMatrix(
        Scanner scanner,
        int processCount,
        int resourceCount,
        String matrixName
    ) {
        System.out.println("--Enter the " + matrixName + " matrix--");
        int[][] matrix = new int[processCount][resourceCount];
        for (int process = 0; process < processCount; process++) {
            System.out.println("For process " + process + ": ");
            for (int resource = 0; resource < resourceCount; resource++) {
                String prompt =
                    "maximum".equals(matrixName)
                        ? "Enter the maximum instances of resource "
                        : "Allocated instances of resource ";
                System.out.println(prompt + resource);
                matrix[process][resource] = scanner.nextInt();
            }
        }
        return matrix;
    }
}