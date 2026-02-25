package com.thealgorithms.dynamicprogramming;

/**
 * Solves the Optimal Job Scheduling problem with:
 * <ul>
 *   <li>precedence constraints between processes</li>
 *   <li>machine-pair-dependent transportation delays</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Optimal_job_scheduling">Optimal job scheduling</a>
 */
public class OptimalJobScheduling {

    private final int numberProcesses;
    private final int numberMachines;
    /**
     * run[i][j] = cost of running process i on machine j
     */
    private final int[][] run;
    /**
     * transfer[m1][m2] = transportation delay from machine m1 to machine m2
     */
    private final int[][] transfer;
    /**
     * cost[i][j] = minimal total cost to execute processes 0..i with process i on machine j
     */
    private final int[][] cost;

    /**
     * @param numberProcesses number of processes (N)
     * @param numberMachines  number of machines (M)
     * @param run             N×M matrix: run[i][j] is the cost of running process i on machine j
     * @param transfer        M×M symmetric matrix: transfer[m1][m2] is the transportation delay
     *                        between machines m1 and m2
     */
    public OptimalJobScheduling(int numberProcesses, int numberMachines, int[][] run, int[][] transfer) {
        this.numberProcesses = numberProcesses;
        this.numberMachines = numberMachines;
        this.run = run;
        this.transfer = transfer;
        this.cost = new int[numberProcesses][numberMachines];
    }

    /**
     * Computes the scheduling costs and prints the resulting cost matrix.
     */
    public void execute() {
        calculateCost();
        showResults();
    }

    /**
     * Fills the {@code cost} matrix with the minimal cost of running each process on each machine.
     */
    private void calculateCost() {
        for (int process = 0; process < numberProcesses; process++) {
            for (int machine = 0; machine < numberMachines; machine++) {
                cost[process][machine] = runningCost(process, machine);
            }
        }
    }

    /**
     * Computes the minimal total cost of executing all processes up to {@code process}, with
     * {@code process} executed on {@code machine}.
     *
     * <p>For process 0, the cost is simply its run cost on the given machine. For any later
     * process, we consider all machines on which the previous process could have run, add the
     * transfer cost from that machine to {@code machine}, and take the minimum.</p>
     *
     * @param process index of the process (0-based)
     * @param machine index of the machine (0-based)
     * @return minimal total cost up to {@code process} with {@code process} on {@code machine}
     */
    private int runningCost(int process, int machine) {
        if (process == 0) {
            // First process: no previous process, so no transfer cost
            return run[process][machine];
        }

        int[] runningCosts = new int[numberMachines];

        // Consider all machines where the previous process could have been executed
        for (int prevMachine = 0; prevMachine < numberMachines; prevMachine++) {
            runningCosts[prevMachine] =
                cost[process - 1][prevMachine]      // cost up to previous process
                    + transfer[prevMachine][machine] // transfer result to current machine
                    + run[process][machine];         // run current process on current machine
        }

        return findMin(runningCosts);
    }

    /**
     * Returns the minimum value in the given array.
     *
     * @param costArr array of costs
     * @return minimum cost
     */
    private int findMin(int[] costArr) {
        int minIndex = 0;
        for (int i = 1; i < costArr.length; i++) {
            if (costArr[i] < costArr[minIndex]) {
                minIndex = i;
            }
        }
        return costArr[minIndex];
    }

    /**
     * Prints the cost matrix to standard output.
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
     * Returns the minimal total cost of executing all processes up to {@code process}, with
     * {@code process} executed on {@code machine}.
     *
     * @param process index of the process (0-based)
     * @param machine index of the machine (0-based)
     * @return minimal total cost for that (process, machine) pair
     */
    public int getCost(int process, int machine) {
        return cost[process][machine];
    }
}