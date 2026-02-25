package com.thealgorithms.others;

import java.util.Scanner;

/**
 * This file contains an implementation of BANKER'S ALGORITHM Wikipedia:
 * https://en.wikipedia.org/wiki/Banker%27s_algorithm
 *
 * The algorithm for finding out whether or not a system is in a safe state can
 * be described as follows: 1. Let Work and Finish be vectors of length ‘m’ and
 * ‘n’ respectively. Initialize: Work= Available Finish [i]=false; for
 * i=1,2,……,n 2. Find an i such that both a) Finish [i]=false b) Need_i<=work
 *
 * if no such i exists goto step (4) 3. Work=Work + Allocation_i Finish[i]= true
 * goto step(2) 4. If Finish[i]=true for all i, then the system is in safe
 * state.
 *
 * Time Complexity: O(n*n*m) Space Complexity: O(n*m) where n = number of
 * processes and m = number of resources.
 *
 * @author AMRITESH ANAND (https://github.com/amritesh19)
 */
public final class BankersAlgorithm {

    private BankersAlgorithm() {}

    /**
     * This method calculates the remaining need of each process.
     */
    static void calculateNeed(
            int[][] need,
            int[][] maxDemand,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                need[processIndex][resourceIndex] =
                        maxDemand[processIndex][resourceIndex] - allocation[processIndex][resourceIndex];
            }
        }
    }

    /**
     * This method checks whether the system is in a safe state or not.
     *
     * @param processes int array of processes (0...n-1), size = n
     * @param available int array of number of instances of each resource, size = m
     * @param maxDemand int matrix(2-D array) of maximum demand of each process in a system, size = n*m
     * @param allocation int matrix(2-D array) of the number of resources of each type currently allocated to each process, size = n*m
     * @param processCount number of total processes, n
     * @param resourceCount number of total resources, m
     *
     * @return boolean if the system is in safe state or not
     */
    static boolean isSystemInSafeState(
            int[] processes,
            int[] available,
            int[][] maxDemand,
            int[][] allocation,
            int processCount,
            int resourceCount) {

        int[][] need = new int[processCount][resourceCount];
        calculateNeed(need, maxDemand, allocation, processCount, resourceCount);

        boolean[] isProcessFinished = new boolean[processCount];
        int[] safeSequence = new int[processCount];

        int[] work = new int[resourceCount];
        System.arraycopy(available, 0, work, 0, resourceCount);

        int safeSequenceIndex = 0;

        // While all processes are not finished or system is not in safe state.
        while (safeSequenceIndex < processCount) {
            boolean foundProcessForSafeSequence = false;

            for (int processIndex = 0; processIndex < processCount; processIndex++) {
                if (!isProcessFinished[processIndex]) {
                    int resourceIndex;

                    for (resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                        if (need[processIndex][resourceIndex] > work[resourceIndex]) {
                            break;
                        }
                    }

                    if (resourceIndex == resourceCount) {
                        for (int k = 0; k < resourceCount; k++) {
                            work[k] += allocation[processIndex][k];
                        }

                        safeSequence[safeSequenceIndex++] = processIndex;
                        isProcessFinished[processIndex] = true;
                        foundProcessForSafeSequence = true;
                    }
                }
            }

            // If we could not find a next process in safe sequence.
            if (!foundProcessForSafeSequence) {
                System.out.print("The system is not in the safe state because lack of resources");
                return false;
            }
        }

        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        return true;
    }

    /**
     * This is main method of Banker's Algorithm
     */
    public static void main(String[] args) {
        int processCount;
        int resourceCount;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        processCount = scanner.nextInt();

        System.out.println("Enter total number of resources");
        resourceCount = scanner.nextInt();

        int[] processes = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            processes[i] = i;
        }

        System.out.println("--Enter the availability of--");

        int[] available = new int[resourceCount];
        for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
            System.out.println("resource " + resourceIndex + ": ");
            available[resourceIndex] = scanner.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");

        int[][] maxDemand = new int[processCount][resourceCount];
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            System.out.println("For process " + processIndex + ": ");
            for (int resourceIndex = 0; resourceIndex < resourceCount; resourceIndex++) {
                System.out.println("Enter the maximum instances of resource " + resourceIndex);
                maxDemand[processIndex][resourceIndex] = scanner.nextInt();
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

        isSystemInSafeState(processes, available, maxDemand, allocation, processCount, resourceCount);

        scanner.close();
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