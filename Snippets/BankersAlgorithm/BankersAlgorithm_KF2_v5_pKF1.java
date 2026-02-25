package com.thealgorithms.others;

import java.util.Scanner;

public final class BankersAlgorithm {

    private BankersAlgorithm() {
    }

    static void calculateNeedMatrix(
            int[][] need,
            int[][] maxDemand,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        for (int process = 0; process < processCount; process++) {
            for (int resource = 0; resource < resourceCount; resource++) {
                need[process][resource] = maxDemand[process][resource] - allocation[process][resource];
            }
        }
    }

    static boolean isSystemInSafeState(
            int[] processIds,
            int[] available,
            int[][] maxDemand,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        int[][] need = new int[processCount][resourceCount];
        calculateNeedMatrix(need, maxDemand, allocation, processCount, resourceCount);

        boolean[] finished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int safeSequencePosition = 0;

        while (safeSequencePosition < processCount) {
            boolean progressMade = false;

            for (int process = 0; process < processCount; process++) {
                if (!finished[process]) {
                    int resource;

                    for (resource = 0; resource < resourceCount; resource++) {
                        if (need[process][resource] > work[resource]) {
                            break;
                        }
                    }

                    if (resource == resourceCount) {
                        for (int currentResource = 0; currentResource < resourceCount; currentResource++) {
                            work[currentResource] += allocation[process][currentResource];
                        }

                        safeSequence[safeSequencePosition++] = process;
                        finished[process] = true;
                        progressMade = true;
                    }
                }
            }

            if (!progressMade) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in a safe state and the safe sequence is: ");
        for (int process = 0; process < processCount; process++) {
            System.out.print("P" + safeSequence[process] + " ");
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        int processCount = scanner.nextInt();

        System.out.println("Enter total number of resources");
        int resourceCount = scanner.nextInt();

        int[] processIds = new int[processCount];
        for (int process = 0; process < processCount; process++) {
            processIds[process] = process;
        }

        System.out.println("--Enter the availability of each resource--");
        int[] available = new int[resourceCount];
        for (int resource = 0; resource < resourceCount; resource++) {
            System.out.println("Resource " + resource + ": ");
            available[resource] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] maxDemand = new int[processCount][resourceCount];
        for (int process = 0; process < processCount; process++) {
            System.out.println("For process " + process + ": ");
            for (int resource = 0; resource < resourceCount; resource++) {
                System.out.println("Enter the maximum instances of resource " + resource);
                maxDemand[process][resource] = scanner.nextInt();
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

        isSystemInSafeState(processIds, available, maxDemand, allocation, processCount, resourceCount);

        scanner.close();
    }
}