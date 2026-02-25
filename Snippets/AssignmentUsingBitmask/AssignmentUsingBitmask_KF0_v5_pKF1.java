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
    private final int[][] memoizedAssignments;
    private final List<List<Integer>> eligiblePersonsPerTask;
    private final int allPersonsAssignedMask;

    /**
     * Constructor for the AssignmentUsingBitmask class.
     *
     * @param personToTasks a list of lists, where each inner list contains the tasks that a person can perform.
     * @param totalTasks    the total number of tasks.
     */
    public AssignmentUsingBitmask(List<List<Integer>> personToTasks, int totalTasks) {
        this.totalTasks = totalTasks;
        int totalPersons = personToTasks.size();

        this.memoizedAssignments = new int[1 << totalPersons][totalTasks + 1];
        for (int[] row : memoizedAssignments) {
            Arrays.fill(row, -1);
        }

        this.eligiblePersonsPerTask = new ArrayList<>(totalTasks + 1);
        for (int taskId = 0; taskId <= totalTasks; taskId++) {
            this.eligiblePersonsPerTask.add(new ArrayList<>());
        }

        this.allPersonsAssignedMask = (1 << totalPersons) - 1;

        for (int personId = 0; personId < totalPersons; personId++) {
            for (int taskId : personToTasks.get(personId)) {
                this.eligiblePersonsPerTask.get(taskId).add(personId);
            }
        }
    }

    /**
     * Counts the ways to assign tasks starting from the given task index with the specified mask.
     *
     * @param assignedPersonsMask the bitmask representing which persons have already been assigned a task.
     * @param currentTaskId       the current task index being processed.
     * @return the number of ways to assign tasks.
     */
    private int countAssignments(int assignedPersonsMask, int currentTaskId) {
        if (assignedPersonsMask == allPersonsAssignedMask) {
            return 1;
        }
        if (currentTaskId > totalTasks) {
            return 0;
        }
        if (memoizedAssignments[assignedPersonsMask][currentTaskId] != -1) {
            return memoizedAssignments[assignedPersonsMask][currentTaskId];
        }

        int totalAssignmentWays = countAssignments(assignedPersonsMask, currentTaskId + 1);

        for (int personId : eligiblePersonsPerTask.get(currentTaskId)) {
            if ((assignedPersonsMask & (1 << personId)) != 0) {
                continue;
            }
            totalAssignmentWays += countAssignments(assignedPersonsMask | (1 << personId), currentTaskId + 1);
        }

        memoizedAssignments[assignedPersonsMask][currentTaskId] = totalAssignmentWays;
        return totalAssignmentWays;
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