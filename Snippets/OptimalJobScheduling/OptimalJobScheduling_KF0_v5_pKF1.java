package com.thealgorithms.dynamicprogramming;

/**
 * This class refers to the Optimal Job Scheduling problem with the following constraints:
 *  - precedence relation between the processes
 *  - machine pair dependent transportation delays
 *
 * https://en.wikipedia.org/wiki/Optimal_job_scheduling
 *
 * author georgioct@csd.auth.gr
 */
public class OptimalJobScheduling {

    private final int processCount;
    private final int machineCount;
    private final int[][] executionTimeByProcessAndMachine;
    private final int[][] transferDelayBetweenMachines;
    private final int[][] cumulativeCostByProcessAndMachine;

    /**
     * Constructor of the class.
     *
     * @param processCount refers to the number of precedent processes (N)
     * @param machineCount refers to the number of different machines at our disposal (M)
     * @param executionTimeByProcessAndMachine N*M matrix refers to the cost of running each process on each machine
     * @param transferDelayBetweenMachines M*M symmetric matrix refers to the transportation delay for each pair of
     *     machines
     */
    public OptimalJobScheduling(
        int processCount,
        int machineCount,
        int[][] executionTimeByProcessAndMachine,
        int[][] transferDelayBetweenMachines
    ) {
        this.processCount = processCount;
        this.machineCount = machineCount;
        this.executionTimeByProcessAndMachine = executionTimeByProcessAndMachine;
        this.transferDelayBetweenMachines = transferDelayBetweenMachines;
        this.cumulativeCostByProcessAndMachine = new int[processCount][machineCount];
    }

    /**
     * Computes the cost of process scheduling to a number of VMs and prints the results.
     */
    public void execute() {
        calculateAllCumulativeCosts();
        printAllCumulativeCosts();
    }

    /**
     * Computes the cumulative cost of running each process on each machine.
     */
    private void calculateAllCumulativeCosts() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                cumulativeCostByProcessAndMachine[processIndex][machineIndex] =
                    calculateCumulativeCostForProcessOnMachine(processIndex, machineIndex);
            }
        }
    }

    /**
     * Returns the minimum cumulative cost of running a certain process on a certain machine.
     * In order for the machine to execute the process, it requires the output of the previously
     * executed process, which may have been executed on the same machine or another one.
     * If the previous process has been executed on another machine, we have to transfer its result,
     * which means extra cost for transferring the data from one machine to another
     * (if the previous process has been executed on the same machine, there is no transport cost).
     *
     * @param processIndex refers to the process index
     * @param machineIndex refers to the machine index
     * @return the minimum cumulative cost of executing the process on the specified machine.
     */
    private int calculateCumulativeCostForProcessOnMachine(int processIndex, int machineIndex) {
        if (processIndex == 0) {
            // First process does not require a previous one to have been executed
            return executionTimeByProcessAndMachine[processIndex][machineIndex];
        }

        int[] cumulativeCostsFromPreviousMachines = new int[machineCount];

        // Compute the cost of executing the previous process on each machine
        for (int previousMachineIndex = 0; previousMachineIndex < machineCount; previousMachineIndex++) {
            cumulativeCostsFromPreviousMachines[previousMachineIndex] =
                cumulativeCostByProcessAndMachine[processIndex - 1][previousMachineIndex]
                    + transferDelayBetweenMachines[previousMachineIndex][machineIndex]
                    + executionTimeByProcessAndMachine[processIndex][machineIndex];
        }

        return findMinimumCost(cumulativeCostsFromPreviousMachines);
    }

    /**
     * Returns the minimum value in the given cost array.
     *
     * @param costsPerMachine an array of size M which refers to the costs of executing a process on each
     *     machine
     * @return the minimum cost
     */
    private int findMinimumCost(int[] costsPerMachine) {
        int indexOfMinimumCost = 0;

        for (int machineIndex = 1; machineIndex < costsPerMachine.length; machineIndex++) {
            if (costsPerMachine[machineIndex] < costsPerMachine[indexOfMinimumCost]) {
                indexOfMinimumCost = machineIndex;
            }
        }
        return costsPerMachine[indexOfMinimumCost];
    }

    /**
     * Prints the overall costs.
     */
    private void printAllCumulativeCosts() {
        for (int processIndex = 0; processIndex < processCount; processIndex++) {
            for (int machineIndex = 0; machineIndex < machineCount; machineIndex++) {
                System.out.print(cumulativeCostByProcessAndMachine[processIndex][machineIndex]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Getter for the running cost of a process on a machine.
     */
    public int getCost(int processIndex, int machineIndex) {
        return cumulativeCostByProcessAndMachine[processIndex][machineIndex];
    }
}