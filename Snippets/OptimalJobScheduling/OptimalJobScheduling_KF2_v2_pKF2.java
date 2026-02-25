package com.thealgorithms.dynamicprogramming;

public class OptimalJobScheduling {

    private final int numberOfProcesses;
    private final int numberOfMachines;
    private final int[][] runTime;
    private final int[][] transferTime;
    private final int[][] totalCost;

    public OptimalJobScheduling(
        int numberOfProcesses,
        int numberOfMachines,
        int[][] runTime,
        int[][] transferTime
    ) {
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfMachines = numberOfMachines;
        this.runTime = runTime;
        this.transferTime = transferTime;
        this.totalCost = new int[numberOfProcesses][numberOfMachines];
    }

    public void execute() {
        computeAllCosts();
        printCosts();
    }

    /**
     * Computes the minimal total cost for every (process, machine) pair.
     */
    private void computeAllCosts() {
        for (int process = 0; process < numberOfProcesses; process++) {
            for (int machine = 0; machine < numberOfMachines; machine++) {
                totalCost[process][machine] = computeCost(process, machine);
            }
        }
    }

    /**
     * Computes the minimal total cost of executing the given process on the given machine.
     *
     * Recurrence:
     *   cost[0][m] = runTime[0][m]
     *   cost[p][m] = min over k (
     *                   cost[p - 1][k]
     *                   + transferTime[k][m]
     *                   + runTime[p][m]
     *               )
     */
    private int computeCost(int process, int machine) {
        if (process == 0) {
            return runTime[process][machine];
        }

        int[] candidateCosts = new int[numberOfMachines];
        for (int previousMachine = 0; previousMachine < numberOfMachines; previousMachine++) {
            candidateCosts[previousMachine] =
                totalCost[process - 1][previousMachine]
                    + transferTime[previousMachine][machine]
                    + runTime[process][machine];
        }
        return min(candidateCosts);
    }

    /**
     * Returns the minimum value in the given array.
     */
    private int min(int[] values) {
        int minIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[minIndex]) {
                minIndex = i;
            }
        }
        return values[minIndex];
    }

    /**
     * Prints the DP table of total costs.
     */
    private void printCosts() {
        for (int process = 0; process < numberOfProcesses; process++) {
            for (int machine = 0; machine < numberOfMachines; machine++) {
                System.out.print(totalCost[process][machine] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getCost(int process, int machine) {
        return totalCost[process][machine];
    }
}