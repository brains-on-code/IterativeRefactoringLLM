package com.thealgorithms.dynamicprogramming;

/**
 * Computes the minimum total processing cost for assigning a sequence of
 * processes to a set of machines, taking into account both:
 * <ul>
 *   <li>runTime[p][m]: time to run process p on machine m</li>
 *   <li>transferTime[m1][m2]: time to transfer from machine m1 to machine m2
 *       between consecutive processes</li>
 * </ul>
 *
 * The dynamic programming table totalCost[p][m] stores the minimum total cost
 * to execute processes 0..p with process p running on machine m.
 */
public class OptimalJobScheduling {

    private final int numberOfProcesses;
    private final int numberOfMachines;

    /** runTime[p][m] = time to run process p on machine m */
    private final int[][] runTime;

    /** transferTime[m1][m2] = time to transfer from machine m1 to machine m2 */
    private final int[][] transferTime;

    /**
     * totalCost[p][m] = minimum total cost to execute processes 0..p
     * with process p assigned to machine m.
     */
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

    /**
     * Runs the dynamic programming algorithm and prints the resulting cost table.
     */
    public void execute() {
        computeAllCosts();
        printCosts();
    }

    /**
     * Fills the totalCost table using dynamic programming.
     */
    private void computeAllCosts() {
        for (int process = 0; process < numberOfProcesses; process++) {
            for (int machine = 0; machine < numberOfMachines; machine++) {
                totalCost[process][machine] = computeCost(process, machine);
            }
        }
    }

    /**
     * Computes totalCost[process][machine].
     *
     * Recurrence:
     * <pre>
     * if process == 0:
     *     totalCost[0][m] = runTime[0][m]
     * else:
     *     totalCost[p][m] = runTime[p][m] +
     *                       min over k ( totalCost[p-1][k] + transferTime[k][m] )
     * </pre>
     */
    private int computeCost(int process, int machine) {
        if (process == 0) {
            return runTime[process][machine];
        }

        int minCost = Integer.MAX_VALUE;
        int previousProcess = process - 1;

        for (int previousMachine = 0; previousMachine < numberOfMachines; previousMachine++) {
            int candidateCost =
                totalCost[previousProcess][previousMachine]
                    + transferTime[previousMachine][machine]
                    + runTime[process][machine];

            if (candidateCost < minCost) {
                minCost = candidateCost;
            }
        }

        return minCost;
    }

    /**
     * Prints the totalCost table, one process per line.
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

    /**
     * Returns the precomputed minimum total cost for the given process and machine.
     */
    public int getCost(int process, int machine) {
        return totalCost[process][machine];
    }
}