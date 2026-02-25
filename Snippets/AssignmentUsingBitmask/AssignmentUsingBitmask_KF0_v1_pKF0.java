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
    private final int[][] dp;
    private final List<List<Integer>> tasksPerTaskId;
    private final int finalMask;
    private final int peopleCount;

    /**
     * Constructor for the AssignmentUsingBitmask class.
     *
     * @param taskPerformed a list of lists, where each inner list contains the tasks that a person can perform.
     * @param total        the total number of tasks.
     */
    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int total) {
        this.totalTasks = total;
        this.peopleCount = taskPerformed.size();
        this.finalMask = (1 << peopleCount) - 1;

        this.dp = new int[1 << peopleCount][totalTasks + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        this.tasksPerTaskId = new ArrayList<>(totalTasks + 1);
        for (int i = 0; i <= totalTasks; i++) {
            this.tasksPerTaskId.add(new ArrayList<>());
        }

        for (int person = 0; person < peopleCount; person++) {
            for (int taskId : taskPerformed.get(person)) {
                if (taskId >= 0 && taskId <= totalTasks) {
                    this.tasksPerTaskId.get(taskId).add(person);
                }
            }
        }
    }

    /**
     * Counts the ways to assign tasks until the given task number with the specified mask.
     *
     * @param mask   the bitmask representing the current state of assignments.
     * @param taskId the current task number being processed.
     * @return the number of ways to assign tasks.
     */
    private int countWaysUntil(int mask, int taskId) {
        if (mask == finalMask) {
            return 1;
        }
        if (taskId > totalTasks) {
            return 0;
        }
        if (dp[mask][taskId] != -1) {
            return dp[mask][taskId];
        }

        int totalWays = countWaysUntil(mask, taskId + 1);

        for (int person : tasksPerTaskId.get(taskId)) {
            if ((mask & (1 << person)) != 0) {
                continue;
            }
            int updatedMask = mask | (1 << person);
            totalWays += countWaysUntil(updatedMask, taskId + 1);
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
        return countWaysUntil(0, 1);
    }
}