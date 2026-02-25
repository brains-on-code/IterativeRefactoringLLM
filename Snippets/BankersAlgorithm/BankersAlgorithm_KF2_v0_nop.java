package com.thealgorithms.others;

import java.util.Scanner;


public final class BankersAlgorithm {
    private BankersAlgorithm() {
    }


    static void calculateNeed(int[][] needArray, int[][] maxArray, int[][] allocationArray, int totalProcess, int totalResources) {
        for (int i = 0; i < totalProcess; i++) {
            for (int j = 0; j < totalResources; j++) {
                needArray[i][j] = maxArray[i][j] - allocationArray[i][j];
            }
        }
    }


    static boolean checkSafeSystem(int[] processes, int[] availableArray, int[][] maxArray, int[][] allocationArray, int totalProcess, int totalResources) {
        int[][] needArray = new int[totalProcess][totalResources];

        calculateNeed(needArray, maxArray, allocationArray, totalProcess, totalResources);

        boolean[] finishProcesses = new boolean[totalProcess];

        int[] safeSequenceArray = new int[totalProcess];

        int[] workArray = new int[totalResources];
        System.arraycopy(availableArray, 0, workArray, 0, totalResources);

        int count = 0;

        while (count < totalProcess) {
            boolean foundSafeSystem = false;
            for (int m = 0; m < totalProcess; m++) {
                if (!finishProcesses[m]) {
                    int j;

                    for (j = 0; j < totalResources; j++) {
                        if (needArray[m][j] > workArray[j]) {
                            break;
                        }
                    }

                    if (j == totalResources) {
                        for (int k = 0; k < totalResources; k++) {
                            workArray[k] += allocationArray[m][k];
                        }

                        safeSequenceArray[count++] = m;

                        finishProcesses[m] = true;

                        foundSafeSystem = true;
                    }
                }
            }

            if (!foundSafeSystem) {
                System.out.print("The system is not in the safe state because lack of resources");
                return false;
            }
        }

        System.out.print("The system is in safe sequence and the sequence is as follows: ");
        for (int i = 0; i < totalProcess; i++) {
            System.out.print("P" + safeSequenceArray[i] + " ");
        }

        return true;
    }


    public static void main(String[] args) {
        int numberOfProcesses;
        int numberOfResources;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter total number of processes");
        numberOfProcesses = sc.nextInt();

        System.out.println("Enter total number of resources");
        numberOfResources = sc.nextInt();

        int[] processes = new int[numberOfProcesses];
        for (int i = 0; i < numberOfProcesses; i++) {
            processes[i] = i;
        }

        System.out.println("--Enter the availability of--");

        int[] availableArray = new int[numberOfResources];
        for (int i = 0; i < numberOfResources; i++) {
            System.out.println("resource " + i + ": ");
            availableArray[i] = sc.nextInt();
        }

        System.out.println("--Enter the maximum matrix--");

        int[][] maxArray = new int[numberOfProcesses][numberOfResources];
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("For process " + i + ": ");
            for (int j = 0; j < numberOfResources; j++) {
                System.out.println("Enter the maximum instances of resource " + j);
                maxArray[i][j] = sc.nextInt();
            }
        }

        System.out.println("--Enter the allocation matrix--");

        int[][] allocationArray = new int[numberOfProcesses][numberOfResources];
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("For process " + i + ": ");
            for (int j = 0; j < numberOfResources; j++) {
                System.out.println("Allocated instances of resource " + j);
                allocationArray[i][j] = sc.nextInt();
            }
        }

        checkSafeSystem(processes, availableArray, maxArray, allocationArray, numberOfProcesses, numberOfResources);

        sc.close();
    }
}

