package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Counts the number of valid ways to assign tasks to people, given which
 * person can perform which task, using bitmasking and dynamic programming.
 */
public final class AssignmentUsingBitmask {

    /** Total number of tasks (1-based indexing). */
    private final int totalTasks;

    /**
     * Memoization table.
     * dp[mask][taskIndex] = number of ways to assign tasks from taskIndex..totalTasks
     * given that 'mask' represents which people are already assigned.
     */
    private final int[][] dp;

    /**
     * taskToPeople.get(task) = list of people who can perform the given task.
     * Index 0 is unused so that tasks are 1-based.
     */
    private final List<List<Integer>> taskToPeople;

    /** Bitmask where all people are assigned (all bits set). */
    private final int allPeopleAssignedMask;

    /**
     * @param taskPerformed taskPerformed[person] = list of tasks that the given person can perform
     * @param totalTasks    total number of tasks (tasks are numbered 1..totalTasks)
     */
    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;

        int peopleCount = taskPerformed.size();
        this.dp = new int[1 << peopleCount][totalTasks + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        this.taskToPeople = new ArrayList<>(totalTasks + 1);
        for (int task = 0; task <= totalTasks; task++) {
            this.taskToPeople.add(new ArrayList<>());
        }

        this.allPeopleAssignedMask = (1 << peopleCount) - 1;

        for (int person = 0; person < peopleCount; person++) {
            for (int task : taskPerformed.get(person)) {
                this.taskToPeople.get(task).add(person);
            }
        }
    }

    /**
     * Computes the number of ways to assign tasks from taskIndex..totalTasks
     * given the current assignment state 'mask'.
     *
     * @param mask      bitmask of already-assigned people
     * @param taskIndex current task index (1-based)
     * @return number of valid assignments for the remaining tasks
     */
    private int countWaysUntil(int mask, int taskIndex) {
        if (mask == allPeopleAssignedMask) {
            return 1;
        }
        if (taskIndex > totalTasks) {
            return 0;
        }

        int cachedResult = dp[mask][taskIndex];
        if (cachedResult != -1) {
            return cachedResult;
        }

        // Option 1: skip this task
        int totalWays = countWaysUntil(mask, taskIndex + 1);

        // Option 2: assign this task to any eligible, unassigned person
        for (int person : taskToPeople.get(taskIndex)) {
            boolean isPersonAlreadyAssigned = (mask & (1 << person)) != 0;
            if (isPersonAlreadyAssigned) {
                continue;
            }
            int updatedMask = mask | (1 << person);
            totalWays += countWaysUntil(updatedMask, taskIndex + 1);
        }

        dp[mask][taskIndex] = totalWays;
        return totalWays;
    }

    /** Returns the total number of valid task assignments. */
    public int countNoOfWays() {
        return countWaysUntil(0, 1);
    }
}