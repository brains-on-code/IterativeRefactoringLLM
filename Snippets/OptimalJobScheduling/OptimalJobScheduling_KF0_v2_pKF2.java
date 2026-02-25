package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic-programming solution to the Optimal Job Scheduling problem with:
 * <ul>
 *   <li>precedence constraints between processes</li>
 *   <li>machine-pair-dependent transportation delays</li>
 * </ul>
 *
 * <p>Given:
 * <ul>
 *   <li>{@code run[i][j]} – cost of running process {@code i} on machine {@code j}</li>
 *   <li>{@code transfer[m1][m2]} – transportation delay from machine {@code m1} to {@code m2}</li>
 * </ul>
 *
 * <p>This class computes:
 * <ul>
 *   <li>{@code cost[i][j]} – minimal total cost to execute processes {@code 0..i}
 *       with process {@code i} executed on machine {@code j}</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Optimal_job_scheduling">Optimal job scheduling</a>
 */
public class OptimalJobScheduling {

    /** Number of processes (N). */
    private final int numberProcesses;

    /** Number of machines (M). */
    private final int numberMachines;

    /** run[i][j] = cost of running process i on machine j. */
    private final int[][] run;

    /** transfer[m1][m2] = transportation delay from machine m1 to machine m2. */
    private final int[][] transfer;

    /**
     * cost[i][j] = minimal total cost to execute processes 0..i
     * with process i executed on machine j.
     */
    private final int[][] cost;

    /**
     * Creates a new scheduler instance.
     *
     * @param numberProcesses number of processes (N)
     * @param numberMachines  number of machines (M)
     * @param run             N×M matrix: run[i][j] is the cost of running process i on machine j
     * @param transfer        M×M (typically symmetric) matrix: transfer[m1][m2] is the
     *                        transportation delay between machines m1 and m2
     */
    public OptimalJobScheduling(int numberProcesses, int numberMachines, int[][] run, int[][] transfer) {
        this.numberProcesses = numberProcesses;
        this.numberMachines = numberMachines;
        this.run = run;
        this.transfer = transfer;
        this.cost = new int[numberProcesses][numberMachines];
    }

    /**
     * Computes the dynamic-programming table and prints the resulting cost matrix.
     */
    public void execute() {
        calculateCostTable();
        printCostTable();
    }

    /**
     * Populates the {@code cost} matrix with the minimal cost of running each process
     * on each machine.
     *
     * <p>For each process {@code p} and machine {@code m}, we compute:
     * <pre>
     * cost[p][m] = min over k in machines {
     *     cost[p - 1][k] + transfer[k][m] + run[p][m]
     * }
     * </pre>
     * with the base case:
     * <pre>
     * cost[0][m] = run[0][m]
     * </pre>
     */
    private void calculateCostTable() {
        for (int process = 0; process < numberProcesses; process++) {
            for (int machine = 0; machine < numberMachines; machine++) {
                cost[process][machine] = computeRunningCost(process, machine);
            }
        }
    }

    /**
     * Computes the minimal total cost of executing all processes up to {@code process},
     * with {@code process} executed on {@code machine}.
     *
     * <p>Base case:
     * <ul>
     *   <li>For process 0, the cost is just its run cost on the given machine.</li>
     * </ul>
     *
     * <p>Recursive case:
     * <ul>
     *   <li>For process {@code p > 0}, consider all machines on which process {@code p - 1}
     *       could have run, add the transfer cost from that machine to {@code machine},
     *       and take the minimum.</li>
     * </ul>
     *
     * @param process index of the process (0-based)
     * @param machine index of the machine (0-based)
     * @return minimal total cost up to {@code process} with {@code process} on {@code machine}
     */
    private int computeRunningCost(int process, int machine) {
        if (process == 0) {
            return run[process][machine];
        }

        int[] candidateCosts = new int[numberMachines];

        for (int prevMachine = 0; prevMachine < numberMachines; prevMachine++) {
            candidateCosts[prevMachine] =
                cost[process - 1][prevMachine]
                    + transfer[prevMachine][machine]
                    + run[process][machine];
        }

        return findMin(candidateCosts);
    }

    /**
     * Returns the minimum value in the given array.
     *
     * @param values array of candidate costs
     * @return minimum cost
     */
    private int findMin(int[] values) {
        int minIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[minIndex]) {
                minIndex = i;
            }
        }
        return values[minIndex];
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