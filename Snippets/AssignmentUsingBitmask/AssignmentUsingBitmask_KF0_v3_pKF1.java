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

    private final int numberOfTasks;
    private final int[][] memoizedWays;
    private final List<List<Integer>> eligiblePersonsByTask;
    private final int allPersonsAssignedMask;

    /**
     * Constructor for the AssignmentUsingBitmask class.
     *
     * @param personToTasks a list of lists, where each inner list contains the tasks that a person can perform.
     * @param totalTasks    the total number of tasks.
     */
    public AssignmentUsingBitmask(List<List<Integer>> personToTasks, int totalTasks) {
        this.numberOfTasks = totalTasks;
        int numberOfPersons = personToTasks.size();

        this.memoizedWays = new int[1 << numberOfPersons][numberOfTasks + 1];
        for (int[] row : memoizedWays) {
            Arrays.fill(row, -1);
        }

        this.eligiblePersonsByTask = new ArrayList<>(numberOfTasks + 1);
        for (int taskId = 0; taskId <= numberOfTasks; taskId++) {
            this.eligiblePersonsByTask.add(new ArrayList<>());
        }

        // Final mask to check if all persons are included
        this.allPersonsAssignedMask = (1 << numberOfPersons) - 1;

        // Build reverse mapping: for each task, which persons can perform it
        for (int personId = 0; personId < numberOfPersons; personId++) {
            for (int taskId : personToTasks.get(personId)) {
                this.eligiblePersonsByTask.get(taskId).add(personId);
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
        if (assignedPersonsMask == allPersonsAssignedMask) {
            return 1;
        }
        if (currentTaskIndex > numberOfTasks) {
            return 0;
        }
        if (memoizedWays[assignedPersonsMask][currentTaskIndex] != -1) {
            return memoizedWays[assignedPersonsMask][currentTaskIndex];
        }

        int totalWays = countAssignments(assignedPersonsMask, currentTaskIndex + 1);

        // Try assigning the current task to all possible persons
        for (int personId : eligiblePersonsByTask.get(currentTaskIndex)) {
            if ((assignedPersonsMask & (1 << personId)) != 0) {
                continue;
            }
            totalWays += countAssignments(assignedPersonsMask | (1 << personId), currentTaskIndex + 1);
        }

        memoizedWays[assignedPersonsMask][currentTaskIndex] = totalWays;
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