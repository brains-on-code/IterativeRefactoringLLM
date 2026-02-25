package com.thealgorithms.others;

import java.util.Scanner;

public final class BankersAlgorithm {

    private BankersAlgorithm() {
    }

    static void calculateNeed(
            int[][] need,
            int[][] maximum,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                need[processIndex][resourceIndex] =
                        maximum[processIndex][resourceIndex] - allocation[processIndex][resourceIndex];
            }
        }
    }

    static boolean isSystemInSafeState(
            int[] processes,
            int[] available,
            int[][] maximum,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        int[][] need = new int[processCount][resourceCount];
        calculateNeed(need, maximum, allocation, processCount, resourceCount);

        boolean[] isProcessFinished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int safeSequenceIndex = 0;

        while (safeSequenceIndex < processCount) {
            boolean foundProcess = false;

            for (int processIndex = 0; processIndex < processCount; processIndex++) {
                if (!isProcessFinished[processIndex]) {
                    int resourceIndex;

                    for (resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                        if (need[processIndex][resourceIndex] > work[resourceIndex]) {
                            break;
                        }
                    }

                    if (resourceIndex == resourceCount) {
                        for (int r = 0; r < resourceCount; r++) {
                            work[r] += allocation[processIndex][r];
                        }

                        safeSequence[safeSequenceIndex++] = processIndex;
                        isProcessFinished[processIndex] = true;
                        foundProcess = true;
                    }
                }
            }

            if (!foundProcess) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in a safe state and the safe sequence is: ");
        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

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

        System.out.println("--Enter the availability of each resource--");
        int[] available = new int[resourceCount];
        for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
            System.out.println("Resource " + resourceIndex + ": ");
            available[resourceIndex] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] maximum = new int[processCount][resourceCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                System.out.println("Enter the maximum instances of resource " + resourceIndex);
                maximum[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocation = new int[processCount][resourceCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                System.out.println("Allocated instances of resource " + resourceIndex);
                allocation[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        isSystemInSafeState(processes, available, maximum, allocation, processCount, resourceCount);

        scanner.close();
    }
}