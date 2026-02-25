package com.thealgorithms.others;

import java.util.Scanner;

public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Utility class
    }

    private static void calculateNeed(
            int[][] need,
            int[][] max,
            int[][] allocation,
            int processCount,
            int resourceCount
    ) {
        for (int p = 0; p < processCount; p++) {
            for (int r = 0; r < resourceCount; r++) {
                need[p][r] = max[p][r] - allocation[p][r];
            }
        }
    }

    private static boolean canProcessBeAllocated(
            int processIndex,
            int[][] need,
            int[] work,
            int resourceCount
    ) {
        for (int r = 0; r < resourceCount; r++) {
            if (need[processIndex][r] > work[r]) {
                return false;
            }
        }
        return true;
    }

    private static void allocateProcessResources(
            int processIndex,
            int[][] allocation,
            int[] work,
            int resourceCount
    ) {
        for (int r = 0; r < resourceCount; r++) {
            work[r] += allocation[processIndex][r];
        }
    }

    private static boolean isSafeState(
            int[] available,
            int[][] max,
            int[][] allocation,
            int processCount,
            int resourceCount
    ) {
        int[][] need = new int[processCount][resourceCount];
        calculateNeed(need, max, allocation, processCount, resourceCount);

        boolean[] finished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int safeIndex = 0;

        while (safeIndex < processCount) {
            boolean foundProcess = false;

            for (int p = 0; p < processCount; p++) {
                if (finished[p]) {
                    continue;
                }

                if (canProcessBeAllocated(p, need, work, resourceCount)) {
                    allocateProcessResources(p, allocation, work, resourceCount);
                    safeSequence[safeIndex++] = p;
                    finished[p] = true;
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
        for (int p = 0; p < processCount; p++) {
            System.out.print("P" + safeSequence[p] + " ");
        }

        return true;
    }

    private static int[] readAvailableResources(Scanner scanner, int resourceCount) {
        System.out.println("--Enter the availability of--");
        int[] available = new int[resourceCount];
        for (int r = 0; r < resourceCount; r++) {
            System.out.println("resource " + r + ": ");
            available[r] = scanner.nextInt();
        }
        return available;
    }

    private static int[][] readMaxMatrix(
            Scanner scanner,
            int processCount,
            int resourceCount
    ) {
        System.out.println("--Enter the maximum matrix--");
        int[][] max = new int[processCount][resourceCount];
        for (int p = 0; p < processCount; p++) {
            System.out.println("For process " + p + ": ");
            for (int r = 0; r < resourceCount; r++) {
                System.out.println("Enter the maximum instances of resource " + r);
                max[p][r] = scanner.nextInt();
            }
        }
        return max;
    }

    private static int[][] readAllocationMatrix(
            Scanner scanner,
            int processCount,
            int resourceCount
    ) {
        System.out.println("--Enter the allocation matrix--");
        int[][] allocation = new int[processCount][resourceCount];
        for (int p = 0; p < processCount; p++) {
            System.out.println("For process " + p + ": ");
            for (int r = 0; r < resourceCount; r++) {
                System.out.println("Allocated instances of resource " + r);
                allocation[p][r] = scanner.nextInt();
            }
        }
        return allocation;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter total number of processes");
            int processCount = scanner.nextInt();

            System.out.println("Enter total number of resources");
            int resourceCount = scanner.nextInt();

            int[] available = readAvailableResources(scanner, resourceCount);
            int[][] max = readMaxMatrix(scanner, processCount, resourceCount);
            int[][] allocation = readAllocationMatrix(scanner, processCount, resourceCount);

            isSafeState(available, max, allocation, processCount, resourceCount);
        }
    }
}