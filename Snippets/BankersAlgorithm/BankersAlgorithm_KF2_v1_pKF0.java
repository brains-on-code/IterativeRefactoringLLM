package com.thealgorithms.others;

import java.util.Scanner;

public final class BankersAlgorithm {

    private BankersAlgorithm() {
        // Utility class
    }

    static void calculateNeed(
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

    static boolean checkSafeSystem(
            int[] processes,
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

                if (resource == resourceCount) {
                    for (int r = 0; r < resourceCount; r++) {
                        work[r] += allocation[process][r];
                    }

                    safeSequence[safeIndex++] = process;
                    finished[process] = true;
                    foundProcess = true;
                }
            }

            if (!foundProcess) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter total number of processes");
            int processCount = scanner.nextInt();

            System.out.println("Enter total number of resources");
            int resourceCount = scanner.nextInt();

            int[] processes = new int[processCount];
            for (int i = 0; i < processCount; i++) {
                processes[i] = i;
            }

            System.out.println("--Enter the availability of--");
            int[] available = new int[resourceCount];
            for (int resource = 0; resource < resourceCount; resource++) {
                System.out.println("resource " + resource + ": ");
                available[resource] = scanner.nextInt();
            }

            System.out.println("--Enter the maximum matrix--");
            int[][] max = new int[processCount][resourceCount];
            for (int process = 0; process < processCount; process++) {
                System.out.println("For process " + process + ": ");
                for (int resource = 0; resource < resourceCount; resource++) {
                    System.out.println("Enter the maximum instances of resource " + resource);
                    max[process][resource] = scanner.nextInt();
                }
            }

            System.out.println("--Enter the allocation matrix--");
            int[][] allocation = new int[processCount][resourceCount];
            for (int process = 0; process < processCount; process++) {
                System.out.println("For process " + process + ": ");
                for (int resource = 0; resource < resourceCount; resource++) {
                    System.out.println("Allocated instances of resource " + resource);
                    allocation[process][resource] = scanner.nextInt();
                }
            }

            checkSafeSystem(processes, available, max, allocation, processCount, resourceCount);
        }
    }
}