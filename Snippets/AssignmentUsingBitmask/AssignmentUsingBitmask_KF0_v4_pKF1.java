package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The AssignmentUsingBitmask class is used to calculate the total number of ways
 * tasks can be distributed among people, given specific constraints on who can perform which tasks.
 * The approach uses bitmasking and dynamic programming to efficiently solve the problem.
 *
 * @author Hardvan
 */
public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] dpCache;
    private final List<List<Integer>> personsEligibleForTask;
    private final int allPersonsMask;

    /**
     * Constructor for the AssignmentUsingBitmask class.
     *
     * @param personToTasks a list of lists, where each inner list contains the tasks that a person can perform.
     * @param totalTasks    the total number of tasks.
     */
    public AssignmentUsingBitmask(List<List<Integer>> personToTasks, int totalTasks) {
        this.totalTasks = totalTasks;
        int totalPersons = personToTasks.size();

        this.dpCache = new int[1 << totalPersons][totalTasks + 1];
        for (int[] row : dpCache) {
            Arrays.fill(row, -1);
        }

        this.personsEligibleForTask = new ArrayList<>(totalTasks + 1);
        for (int taskIndex = 0; taskIndex <= totalTasks; taskIndex++) {
            this.personsEligibleForTask.add(new ArrayList<>());
        }

        // Final mask to check if all persons are included
        this.allPersonsMask = (1 << totalPersons) - 1;

        // Build reverse mapping: for each task, which persons can perform it
        for (int personIndex = 0; personIndex < totalPersons; personIndex++) {
            for (int taskIndex : personToTasks.get(personIndex)) {
                this.personsEligibleForTask.get(taskIndex).add(personIndex);
            }
        }
    }

    /**
     * Counts the ways to assign tasks starting from the given task index with the specified mask.
     *
     * @param assignedPersonsMask the bitmask representing which persons have already been assigned a task.
     * @param currentTaskIndex    the current task index being processed.
     * @return the number of ways to assign tasks.
     */
    private int countAssignments(int assignedPersonsMask, int currentTaskIndex) {
        if (assignedPersonsMask == allPersonsMask) {
            return 1;
        }
        if (currentTaskIndex > totalTasks) {
            return 0;
        }
        if (dpCache[assignedPersonsMask][currentTaskIndex] != -1) {
            return dpCache[assignedPersonsMask][currentTaskIndex];
        }

        int totalWays = countAssignments(assignedPersonsMask, currentTaskIndex + 1);

        // Try assigning the current task to all possible persons
        for (int personIndex : personsEligibleForTask.get(currentTaskIndex)) {
            if ((assignedPersonsMask & (1 << personIndex)) != 0) {
                continue;
            }
            totalWays += countAssignments(assignedPersonsMask | (1 << personIndex), currentTaskIndex + 1);
        }

        dpCache[assignedPersonsMask][currentTaskIndex] = totalWays;
        return totalWays;
    }

    /**
     * Counts the total number of ways to distribute tasks among persons.
     *
     * @return the total number of ways to distribute tasks.
     */
    public int countNumberOfWays() {
        return countAssignments(0, 1);
    }
}