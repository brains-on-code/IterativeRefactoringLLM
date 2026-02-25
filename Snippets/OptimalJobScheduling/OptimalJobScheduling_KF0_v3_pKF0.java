package com.thealgorithms.dynamicprogramming;

/**
 * Optimal Job Scheduling with:
 *  - precedence relations between processes
 *  - machine-pair-dependent transportation delays
 *
 * https://en.wikipedia.org/wiki/Optimal_job_scheduling
 */
public class OptimalJobScheduling {

    private final int processCount;
    private final int machineCount;
    private final int[][] runCost;
    private final int[][] transferCost;
    private final int[][] totalCost;

    /**
     * @param processCount number of processes (N)
     * @param machineCount number of machines (M)
     * @param runCost N×M matrix: runCost[i][j] is the cost of running process i on machine j
     * @param transferCost M×M symmetric matrix: transferCost[i][j] is the transportation delay
     *                     between machine i and machine j
     */
    public OptimalJobScheduling(int processCount, int machineCount, int[][] runCost, int[][] transferCost) {
        this.processCount = processCount;
        this.machineCount = machineCount;
        this.runCost = runCost;
        this.transferCost = transferCost;
        this.totalCost = new int[processCount][machineCount];
    }

    /** Computes the scheduling costs and prints the results. */
    public void execute() {
        computeAllCosts();
        printResults();
    }

    /** Computes the cost of running each process on each machine. */
    private void computeAllCosts() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                totalCost[processIndex][machineIndex] = computeCostFor(processIndex, machineIndex);
            }
        }
    }

    /**
     * Returns the minimum cost of running a given process on a given machine.
     *
     * For processIndex > 0, the machine requires the output of the previous process,
     * which may have been executed on any machine. If the previous process ran
     * on a different machine, we add the transfer cost.
     *
     * @param processIndex index of the process
     * @param machineIndex index of the machine
     * @return minimum cost of executing the process on the given machine
     */
    private int computeCostFor(int processIndex, int machineIndex) {
        if (processIndex == 0) {
            return runCost[processIndex][machineIndex];
        }

        int currentRunCost = runCost[processIndex][machineIndex];
        int minCost = Integer.MAX_VALUE;

        for (int previousMachineIndex = 0; previousMachineIndex < machineCount; previousMachineIndex++) {
            int candidateCost = computeCandidateCost(processIndex, machineIndex, previousMachineIndex, currentRunCost);
            minCost = Math.min(minCost, candidateCost);
        }

        return minCost;
    }

    private int computeCandidateCost(int processIndex, int machineIndex, int previousMachineIndex, int currentRunCost) {
        int previousTotalCost = totalCost[processIndex - 1][previousMachineIndex];
        int currentTransferCost = transferCost[previousMachineIndex][machineIndex];
        return previousTotalCost + currentTransferCost + currentRunCost;
    }

    /** Prints the overall costs matrix. */
    private void printResults() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            printProcessRow(processIndex);
        }
        System.out.println();
    }

    private void printProcessRow(int processIndex) {
        for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
            System.out.print(totalCost[processIndex][machineIndex]);
            System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * Getter for the running cost of a process on a machine.
     *
     * @param processIndex index of the process
     * @param machineIndex index of the machine
     * @return totalCost[processIndex][machineIndex]
     */
    public int getCost(int processIndex, int machineIndex) {
        return totalCost[processIndex][machineIndex];
    }
}