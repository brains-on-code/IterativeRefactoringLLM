package com.thealgorithms.dynamicprogramming;

/**
 * Optimal Job Scheduling with:
 *  - precedence relations between processes
 *  - machine-pair-dependent transportation delays
 *
 * https://en.wikipedia.org/wiki/Optimal_job_scheduling
 */
public class OptimalJobScheduling {

    private final int numberProcesses;
    private final int numberMachines;
    private final int[][] run;
    private final int[][] transfer;
    private final int[][] cost;

    /**
     * @param numberProcesses number of processes (N)
     * @param numberMachines number of machines (M)
     * @param run N×M matrix: run[i][j] is the cost of running process i on machine j
     * @param transfer M×M symmetric matrix: transfer[i][j] is the transportation delay
     *                 between machine i and machine j
     */
    public OptimalJobScheduling(int numberProcesses, int numberMachines, int[][] run, int[][] transfer) {
        this.numberProcesses = numberProcesses;
        this.numberMachines = numberMachines;
        this.run = run;
        this.transfer = transfer;
        this.cost = new int[numberProcesses][numberMachines];
    }

    /** Computes the scheduling costs and prints the results. */
    public void execute() {
        calculateCost();
        showResults();
    }

    /** Computes the cost of running each process on each machine. */
    private void calculateCost() {
        for (int process = 0; process < numberProcesses; process++) {
            for (int machine = 0; machine < numberMachines; machine++) {
                cost[process][machine] = computeRunningCost(process, machine);
            }
        }
    }

    /**
     * Returns the minimum cost of running a given process on a given machine.
     *
     * For process > 0, the machine requires the output of the previous process,
     * which may have been executed on any machine. If the previous process ran
     * on a different machine, we add the transfer cost.
     *
     * @param process index of the process
     * @param machine index of the machine
     * @return minimum cost of executing the process on the given machine
     */
    private int computeRunningCost(int process, int machine) {
        if (process == 0) {
            // First process: no dependency on previous processes
            return run[process][machine];
        }

        int minCost = Integer.MAX_VALUE;
        int baseRunCost = run[process][machine];

        // Consider all machines where the previous process could have run
        for (int prevMachine = 0; prevMachine < numberMachines; prevMachine++) {
            int previousCost = cost[process - 1][prevMachine];
            int transferCost = transfer[prevMachine][machine];
            int totalCost = previousCost + transferCost + baseRunCost;

            if (totalCost < minCost) {
                minCost = totalCost;
            }
        }

        return minCost;
    }

    /**
     * Prints the overall costs matrix.
     */
    private void showResults() {
        for (int process = 0; process < numberProcesses; process++) {
            for (int machine = 0; machine < numberMachines; machine++) {
                System.out.print(cost[process][machine]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Getter for the running cost of a process on a machine.
     *
     * @param process index of the process
     * @param machine index of the machine
     * @return cost[process][machine]
     */
    public int getCost(int process, int machine) {
        return cost[process][machine];
    }
}