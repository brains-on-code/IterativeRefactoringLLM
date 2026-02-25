package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AssignmentUsingBitmask {

    private final int totalTasks;
    private final int[][] dp;
    private final List<List<Integer>> tasksPerIndex;
    private final int finalMask;

    public AssignmentUsingBitmask(List<List<Integer>> taskPerformed, int totalTasks) {
        this.totalTasks = totalTasks;

        int peopleCount = taskPerformed.size();
        this.dp = new int[1 << peopleCount][totalTasks + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        this.tasksPerIndex = new ArrayList<>(totalTasks + 1);
        for (int i = 0; i <= totalTasks; i++) {
            this.tasksPerIndex.add(new ArrayList<>());
        }

        this.finalMask = (1 << peopleCount) - 1;

        for (int person = 0; person < peopleCount; person++) {
            for (int task : taskPerformed.get(person)) {
                this.tasksPerIndex.get(task).add(person);
            }
        }
    }

    private int countWays(int mask, int taskIndex) {
        if (mask == finalMask) {
            return 1;
        }
        if (taskIndex > totalTasks) {
            return 0;
        }
        if (dp[mask][taskIndex] != -1) {
            return dp[mask][taskIndex];
        }

        int totalWays = countWays(mask, taskIndex + 1);

        for (int person : tasksPerIndex.get(taskIndex)) {
            if ((mask & (1 << person)) == 0) {
                totalWays += countWays(mask | (1 << person), taskIndex + 1);
            }
        }

        dp[mask][taskIndex] = totalWays;
        return totalWays;
    }

    public int countNoOfWays() {
        return countWays(0, 1);
    }
}