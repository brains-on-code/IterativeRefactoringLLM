package com.thealgorithms.others;

import java.util.Scanner;

public final class BankersAlgorithm {

    private BankersAlgorithm() {
    }

    static void calculateNeedMatrix(
            int[][] needMatrix,
            int[][] maxDemandMatrix,
            int[][] allocationMatrix,
            int processCount,
            int resourceCount) {

        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                needMatrix[processIndex][resourceIndex] =
                        maxDemandMatrix[processIndex][resourceIndex] - allocationMatrix[processIndex][resourceIndex];
            }
        }
    }

    static boolean isSystemInSafeState(
            int[] processIds,
            int[] availableResources,
            int[][] maxDemandMatrix,
            int[][] allocationMatrix,
            int processCount,
            int resourceCount) {

        int[][] needMatrix = new int[processCount][resourceCount];
        calculateNeedMatrix(needMatrix, maxDemandMatrix, allocationMatrix, processCount, resourceCount);

        boolean[] isProcessFinished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] workResources = new int[resourceCount];
        System.arraycopy(availableResources, 0, workResources, 0, resourceCount);

        int safeSequenceIndex = 0;

        while (safeSequenceIndex < processCount) {
            boolean progressMadeInThisIteration = false;

            for (int processIndex = 0; processIndex < processCount; processIndex++) {
                if (!isProcessFinished[processIndex]) {
                    int resourceIndex;

                    for (resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                        if (needMatrix[processIndex][resourceIndex] > workResources[resourceIndex]) {
                            break;
                        }
                    }

                    if (resourceIndex == resourceCount) {
                        for (int currentResourceIndex = 0; currentResourceIndex < resourceCount; currentResourceIndex++) {
                            workResources[currentResourceIndex] += allocationMatrix[processIndex][currentResourceIndex];
                        }

                        safeSequence[safeSequenceIndex++] = processIndex;
                        isProcessFinished[processIndex] = true;
                        progressMadeInThisIteration = true;
                    }
                }
            }

            if (!progressMadeInThisIteration) {
                System.out.print("The system is not in the safe state because of lack of resources");
                return false;
            }
        }

        System.out.print("The system is in a safe state and the safe sequence is: ");
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.print("P" + safeSequence[processIndex] + " ");
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
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            processIds[processIndex] = processIndex;
        }

        System.out.println("--Enter the availability of each resource--");
        int[] availableResources = new int[resourceCount];
        for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
            System.out.println("Resource " + resourceIndex + ": ");
            availableResources[resourceIndex] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");
        int[][] maxDemandMatrix = new int[processCount][resourceCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                System.out.println("Enter the maximum instances of resource " + resourceIndex);
                maxDemandMatrix[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");
        int[][] allocationMatrix = new int[processCount][resourceCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                System.out.println("Allocated instances of resource " + resourceIndex);
                allocationMatrix[processIndex][resourceIndex] = scanner.nextInt();
            }
        }

        isSystemInSafeState(processIds, availableResources, maxDemandMatrix, allocationMatrix, processCount, resourceCount);

        scanner.close();
    }
}