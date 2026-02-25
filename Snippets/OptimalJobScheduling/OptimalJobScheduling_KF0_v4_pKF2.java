package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic-programming solution to the Optimal Job Scheduling problem with:
 *
 * <ul>
 *   <li>precedence constraints between processes</li>
 *   <li>machine-pair-dependent transportation delays</li>
 * </ul>
 *
 * <p>Given:
 *
 * <ul>
 *   <li>{@code run[i][j]} – cost of running process {@code i} on machine {@code j}</li>
 *   <li>{@code transfer[m1][m2]} – transportation delay from machine {@code m1} to {@code m2}</li>
 * </ul>
 *
 * <p>This class computes:
 *
 * <ul>
 *   <li>{@code cost[i][j]} – minimal total cost to execute processes {@code 0..i}
 *       with process {@code i} executed on machine {@code j}</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Optimal_job_scheduling">Optimal job scheduling</a>
 */
public class OptimalJobScheduling {

    /** Total number of processes. */
    private final int numberProcesses;

    /** Total number of machines. */
    private final int numberMachines;

    /** {@code run[i][j]} – cost of running process {@code i} on machine {@code j}. */
    private final int[][] run;

    /** {@code transfer[m1][m2]} – transportation delay from machine {@code m1} to {@code m2}. */
    private final int[][] transfer;

    /**
     * Dynamic-programming table:
     * {@code cost[i][j]} – minimal total cost to execute processes {@code 0..i}
     * with process {@code i} executed on machine {@code j}.
     */
    private final int[][] cost;

    /**
     * Constructs a new scheduler.
     *
     * @param numberProcesses number of processes
     * @param numberMachines  number of machines
     * @param run             N×M matrix: {@code run[i][j]} is the cost of running process {@code i} on machine {@code j}
     * @param transfer        M×M matrix: {@code transfer[m1][m2]} is the transportation delay between machines
     *                        {@code m1} and {@code m2}
     */
    public OptimalJobScheduling(int numberProcesses, int numberMachines, int[][] run, int[][] transfer) {
        this.numberProcesses = numberProcesses;
        this.numberMachines = numberMachines;
        this.run = run;
        this.transfer = transfer;
        this.cost = new int[numberProcesses][numberMachines];
    }

    /**
     * Computes the DP table and prints the resulting cost matrix.
     */
    public void execute() {
        fillCostTable();
        printCostTable();
    }

    /**
     * Fills the {@code cost} matrix with the minimal cost of running each process
     * on each machine.
     *
     * <p>Recurrence:</p>
     *
     * <pre>{@code
     * cost[p][m] = min over k in machines {
     *     cost[p - 1][k] + transfer[k][m] + run[p][m]
     * }
     *
     * Base case:
     * cost[0][m] = run[0][m]
     * }</pre>
     */
    private void fillCostTable() {
        for (int process = 0; process < numberProcesses; process++) {
            for (int machine = 0; machine < numberMachines; machine++) {
                cost[process][machine] = computeCostFor(process, machine);
            }
        }
    }

    /**
     * Computes the minimal total cost of executing all processes up to {@code process},
     * with {@code process} executed on {@code machine}.
     *
     * @param process index of the process (0-based)
     * @param machine index of the machine (0-based)
     * @return minimal total cost up to {@code process} with {@code process} on {@code machine}
     */
    private int computeCostFor(int process, int machine) {
        if (process == 0) {
            return run[process][machine];
        }

        int minCost = Integer.MAX_VALUE;

        for (int prevMachine = 0; prevMachine < numberMachines; prevMachine++) {
            int candidateCost =
                cost[process - 1][prevMachine]
                    + transfer[prevMachine][machine]
                    + run[process][machine];

            if (candidateCost < minCost) {
                minCost = candidateCost;
            }
        }

        return minCost;
    }

    /**
     * Prints the cost matrix to standard output.
     *
     * <p>Each row corresponds to a process, and each column to a machine.</p>
     */
    private void printCostTable() {
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
     * Returns the minimal total cost of executing all processes up to {@code process},
     * with {@code process} executed on {@code machine}.
     *
     * @param process index of the process (0-based)
     * @param machine index of the machine (0-based)
     * @return minimal total cost for that (process, machine) pair
     */
    public int getCost(int process, int machine) {
        return cost[process][machine];
    }
}