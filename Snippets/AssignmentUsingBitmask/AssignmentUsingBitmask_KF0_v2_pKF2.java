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
     * dp[mask][taskNo] = number of ways to assign tasks from taskNo..totalTasks
     * given that 'mask' represents which people are already assigned.
     */
    private final int[][] dp;

    /**
     * taskToPeople.get(t) = list of people who can perform task t.
     * Index 0 is unused so that tasks are 1-based.
     */
    private final List<List<Integer>> taskToPeople;

    /** Bitmask where all people are assigned (all bits set). */
    private final int allPeopleAssignedMask;

    /**
     * @param taskPerformed taskPerformed[i] = list of tasks that person i can perform
     * @param total         total number of tasks (tasks are numbered 1..total)
     */
    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int total) {
        this.totalTasks = total;

        int peopleCount = taskPerformed.size();
        this.dp = new int[1 << peopleCount][total + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        this.taskToPeople = new ArrayList<>(totalTasks + 1);
        for (int i = 0; i <= totalTasks; i++) {
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
     * Returns the number of ways to assign tasks from taskNo..totalTasks
     * given the current assignment state 'mask'.
     *
     * @param mask   bitmask of already-assigned people
     * @param taskNo current task index (1-based)
     */
    private int countWaysUntil(int mask, int taskNo) {
        if (mask == allPeopleAssignedMask) {
            return 1;
        }
        if (taskNo > totalTasks) {
            return 0;
        }
        if (dp[mask][taskNo] != -1) {
            return dp[mask][taskNo];
        }

        int totalWays = countWaysUntil(mask, taskNo + 1);

        for (int person : taskToPeople.get(taskNo)) {
            boolean isPersonAlreadyAssigned = (mask & (1 << person)) != 0;
            if (isPersonAlreadyAssigned) {
                continue;
            }
            int updatedMask = mask | (1 << person);
            totalWays += countWaysUntil(updatedMask, taskNo + 1);
        }

        dp[mask][taskNo] = totalWays;
        return totalWays;
    }

    /** Returns the total number of valid task assignments. */
    public int countNoOfWays() {
        return countWaysUntil(0, 1);
    }
}