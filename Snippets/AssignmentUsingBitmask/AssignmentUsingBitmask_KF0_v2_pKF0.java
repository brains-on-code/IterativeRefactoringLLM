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
    private final int peopleCount;
    private final int finalMask;
    private final int[][] dp;
    private final List<List<Integer>> peoplePerTaskId;

    /**
     * Constructor for the AssignmentUsingBitmask class.
     *
     * @param taskPerformed a list of lists, where each inner list contains the tasks that a person can perform.
     * @param totalTasks    the total number of tasks.
     */
    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;
        this.peopleCount = taskPerformed.size();
        this.finalMask = (1 << peopleCount) - 1;

        this.dp = new int[1 << peopleCount][totalTasks + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        this.peoplePerTaskId = new ArrayList<>(totalTasks + 1);
        for (int taskId = 0; taskId <= totalTasks; taskId++) {
            peoplePerTaskId.add(new ArrayList<>());
        }

        for (int person = 0; person < peopleCount; person++) {
            for (int taskId : taskPerformed.get(person)) {
                if (taskId >= 0 && taskId <= totalTasks) {
                    peoplePerTaskId.get(taskId).add(person);
                }
            }
        }
    }

    /**
     * Counts the ways to assign tasks up to the given taskId with the specified mask.
     *
     * @param mask   the bitmask representing the current state of assignments.
     * @param taskId the current task number being processed.
     * @return the number of ways to assign tasks.
     */
    private int countWays(int mask, int taskId) {
        if (mask == finalMask) {
            return 1;
        }
        if (taskId > totalTasks) {
            return 0;
        }
        if (dp[mask][taskId] != -1) {
            return dp[mask][taskId];
        }

        int totalWays = countWays(mask, taskId + 1);

        for (int person : peoplePerTaskId.get(taskId)) {
            if ((mask & (1 << person)) == 0) {
                int updatedMask = mask | (1 << person);
                totalWays += countWays(updatedMask, taskId + 1);
            }
        }

        dp[mask][taskId] = totalWays;
        return totalWays;
    }

    /**
     * Counts the total number of ways to distribute tasks among persons.
     *
     * @return the total number of ways to distribute tasks.
     */
    public int countNoOfWays() {
        return countWays(0, 1);
    }
}